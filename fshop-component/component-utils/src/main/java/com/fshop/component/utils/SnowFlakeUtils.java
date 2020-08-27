/*
 * Copyright [2020] [rannuo]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.utils;

/**
 * Description：基于雪花算法的分布式ID生成工具
 * 雪花算法简单Description:
 * + 最高位是符号位，始终为0，不可用。
 * + 41位的时间序列，精确到毫秒级，41位的长度可以使用69年。时间位还有一个很重要的作用是可以根据时间进行排序。
 * + 10位节点部分，5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点。
 * + 12位的计数序列号，序列号即一系列的自增ID，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号。
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public class SnowFlakeUtils {

    /**
     * 开始时间截 (2019-01-16)
     */
    private static final long START_TIME = 1547651553531L;
    /**
     * 机器标识位数
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据中心标识位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    /**
     * 最大机器标识ID，结果是31 (移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 最大数据中心标识ID，结果是31
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 序列号部分12位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    private void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    private static class SingletonHolder {
        private static final SnowFlakeUtils INSTANCE = new SnowFlakeUtils();
    }

    public static SnowFlakeUtils getInstance(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "WorkerId can't be greater than %d or less than 0",
                    MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format(
                    "DataCenterId can't be greater than %d or less than 0",
                    MAX_DATA_CENTER_ID));
        }
        SingletonHolder.INSTANCE.setWorkerId(workerId);
        SingletonHolder.INSTANCE.setDataCenterId(dataCenterId);
        return SingletonHolder.INSTANCE;
    }

    private SnowFlakeUtils() {
    }

    public static SnowFlakeUtils getInstance() {
        return getInstance(0L, 0L);
    }

    /**
     * 获取雪花ID
     */
    public static long uniqueId() {
        return getInstance().nextId();
    }

    /**
     * 获取雪花ID
     */
    public static String uniqueIdStr() {
        return String.valueOf(uniqueId());
    }

    /**
     * 获取雪花ID（Hex）
     */
    public static String uniqueIdHex() {
        return String.format("%016x", uniqueId());
    }

    /**
     * 获得下一个ID
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIME) << TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取以毫秒为单位的当前时间
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

//    public static void main(String[] args) throws InterruptedException {
//        int threadNum = 10;
//        int num = 10000;
//        int repeatNum = 0;
//        List<Long> ids = Collections.synchronizedList(new LinkedList<>());
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
//        ExecutorService executorService = new ThreadPoolExecutor(threadNum, 2 * threadNum,
//                1L, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(3),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
//        for (int i = 0; i < threadNum; i++) {
//            executorService.submit(() -> {
//                for (int j = 0; j < num; j++) {
//                    ids.add(SnowFlakeUtils.getInstance().nextId());
//                }
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        executorService.shutdown();
//        Map<Long, Integer> map = new TreeMap<>();
//        for (int i = 0; i < threadNum * num; i++) {
//            map.merge(ids.get(i), 1, Integer::sum);
//        }
//        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
//            if (entry.getValue() > 1) {
//                repeatNum++;
//                System.out.println("repeatId=" + entry.getKey() + "\tnum=" + entry.getValue());
//            }
//        }
//        System.out.println("repeatNum=" + repeatNum);
//    }
}
