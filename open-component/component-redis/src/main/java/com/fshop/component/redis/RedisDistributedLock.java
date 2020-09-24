/*
 * Copyright 2020 rannuo1010@gmail.com
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Description：基于Redis的分布式锁
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
@Component
public class RedisDistributedLock {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 释放锁lua脚本，原子操作
     */
    private static final String UNLOCK_LUA_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) " +
                    "else return 0 end";

    /**
     * 获取分布式锁（原子操作：利用set指令参数，同时把setnx和expire合成一条指令）
     *
     * @param lockKey   锁的key
     * @param requestId 唯一标识（解铃还须系铃人，解锁依据）
     * @param expire    过期时间
     * @param timeUnit  时间单位
     * @return 获取锁成功返回true，否则返回false
     */
    public boolean lock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
        try {
            RedisCallback<Boolean> callback = (connection) -> connection.set(
                    lockKey.getBytes(StandardCharsets.UTF_8),
                    requestId.getBytes(StandardCharsets.UTF_8),
                    Expiration.seconds(timeUnit.toSeconds(expire)),
                    RedisStringCommands.SetOption.SET_IF_ABSENT);
            return (boolean) redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("An exception occurred while acquiring the lock", e);
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的key
     * @param requestId 唯一标识
     * @return 释放锁成功返回true，否则返回false
     */
    public boolean unLock(String lockKey, String requestId) {
        try {
            RedisCallback<Boolean> callback = (connection) -> connection.eval(
                    UNLOCK_LUA_SCRIPT.getBytes(), ReturnType.BOOLEAN, 1,
                    lockKey.getBytes(StandardCharsets.UTF_8), requestId.getBytes(StandardCharsets.UTF_8));
            return (boolean) redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("An exception occurred while release the lock", e);
        }
        return false;
    }

    /**
     * 获取锁的value值
     *
     * @param lockKey 锁的key
     * @return 锁的value值
     */
    public String get(String lockKey) {
        RedisCallback<String> callback = (connection) -> new String(
                Objects.requireNonNull(connection.get(lockKey.getBytes())), StandardCharsets.UTF_8);
        return (String) redisTemplate.execute(callback);
    }

    /**
     * 通过分布式锁控制指定方法执行
     *
     * @param consumer
     * @param lockKey
     * @param lockValue
     * @param expire
     * @param timeUnit
     */
    public <T> void executeWithLock(Consumer<T> consumer, String lockKey, String lockValue, long expire, TimeUnit timeUnit) {
        try {
            boolean lock = lock(lockKey, lockValue, expire, timeUnit);
            if (lock) {
                consumer.accept(null);
            }
        } catch (Exception e) {
            log.error("带锁执行异常，lockKey={} | lockValue={}", lockKey, lockValue, e);
        } finally {
            unLock(lockKey, lockValue);
        }
    }

}
