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
package com.fshop.component.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Description：Redis工具类
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Component
@SuppressWarnings("unchecked")
public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * set数据
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void set(K key, V value) {
        set(key, value, -1, null);
    }

    /**
     * set数据（支持指定过期时间）
     *
     * @param key      键
     * @param value    值
     * @param timeout  超时时间（小等于0：代表不超时）
     * @param timeUnit 时间单位
     */
    public <K, V> void set(K key, V value, long timeout, TimeUnit timeUnit) {
        Assert.notNull(key, "Key cannot be null");
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * get数据
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> V get(K key) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 获取符合条件的key
     * Redis是单线程处理，keys命令在KEY数量较多时，操作效率极低【时间复杂度为O(N)】，会严重阻塞线上其它命令的正常请求
     * 使用scan命令代替keys命令
     *
     * @param pattern 表达式
     * @return 匹配到的key set集合
     */
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        scan(pattern, item -> keys.add(new String(item, StandardCharsets.UTF_8)));
        return keys;
    }

    /**
     * 删除指定前缀的key
     *
     * @param keyPrefix key前缀
     */
    public void removeAll(String keyPrefix) {
        String pattern = keyPrefix + "*";
        Set<String> keys = keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 执行scan命令
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    public void scan(String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(
                    ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 添加值到给定的布隆过滤器
     *
     * @param bloomFilter
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void addByBloomFilter(BloomFilter<T> bloomFilter, String key, T value) {
        int[] offset = bloomFilter.calcHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 判断值是否存在于给定的布隆过滤器
     *
     * @param bloomFilter
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean includeByBloomFilter(BloomFilter<T> bloomFilter, String key, T value) {
        int[] offset = bloomFilter.calcHashOffset(value);
        for (int i : offset) {
            Boolean bit = redisTemplate.opsForValue().getBit(key, i);
            if (bit != null && !bit) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向list最左边添加元素
     *
     * @param key
     * @param value
     */
    public <K, V> void leftPush(K key, V value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向list最左边弹出元素
     *
     * @param key
     * @param timeout  超时时间，没有元素时会阻塞直到超时返回null，为0则会一直阻塞
     * @param timeUnit
     * @param <V>
     * @return
     */
    public <K, V> V rightPop(K key, int timeout, TimeUnit timeUnit) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        return listOperations.rightPop(key, timeout, timeUnit);
    }

    /**
     * 执行LUA脚本
     *
     * @param script
     * @param keys
     * @param args
     * @param <T>
     * @param <K>
     * @return
     */
    public <T, K> T executeScript(RedisScript<T> script, List<K> keys, Object... args) {
        return (T) redisTemplate.execute(script, keys, args);
    }

    public <K, HK, HV> void hashPut(K key, HK hKey, HV hValue) {
        redisTemplate.boundHashOps(key).put(hKey, hValue);
    }

    public <K, HK, HV> HV hashGet(K key, HK hKey) {
        return (HV) redisTemplate.boundHashOps(key).get(hKey);
    }

}
