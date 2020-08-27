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
package com.fshop.rest.manager;

import com.fshop.component.redis.RedisDistributedLock;
import com.fshop.component.redis.RedisUtils;
import com.fshop.component.utils.IdUtils;
import com.fshop.order.model.bo.SeckillRetBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.fshop.order.constant.OrderConstants.SECKILL_RESULT_KEY_PREFIX;

/**
 * Description: 订单缓存Manager
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
@Component
public class OrderCacheService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisDistributedLock distributedLock;
    @Autowired
    private OrderRpcManager orderRpcService;

    /**
     * 秒杀-产品数量前缀
     */
    private static final String SECKILL_NUMBER_KEY_PREFIX = "seckill_number:";
    /**
     * 秒杀锁前缀：初始化数量缓存
     */
    private static final String SECKILL_INIT_NUMBER_CACHE_LOCK_KEY_PREFIX = "seckill_lock_init_number_cache:";
    /**
     * 秒杀-验证码前缀
     */
    private static final String SECKILL_VERIFY_CODE_KEY_PREFIX = "seckill_verify_code:";
    /**
     * 秒杀-path
     */
    private static final String SECKILL_PATH_KEY_PREFIX = "seckill_path:";
    /**
     * Lua脚本，先获取指定产品的秒杀数量，再递减
     */
    private static final String DESC_LUA_SCRIPT = "local remain_num = redis.call('get', KEYS[1]); "
            + " if remain_num then "
            + "     if remain_num - ARGV[1] >= 0 then return redis.call('decrby', KEYS[1], ARGV[1]); "
            + "     else return -1; end; "
            + " else return nil; end;";

    /**
     * 扣减库存
     *
     * @param productId 商品ID
     * @param num       数量
     * @return 扣减库存结果
     */
    public Long deductStock(long productId, long num) {
        String key = SECKILL_NUMBER_KEY_PREFIX + productId;
        // Lua脚本原子更新库存数量
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(DESC_LUA_SCRIPT);
        redisScript.setResultType(Long.class);
        Long remainNum = redisUtils.executeScript(redisScript, Collections.singletonList(key), num);
        if (remainNum == null) {
            // 从数据库获取商品数量并设置缓存
            cacheSeckillNum(productId);
            // 扣减
            remainNum = redisUtils.executeScript(redisScript, Collections.singletonList(key), num);
        }
        return remainNum;
    }

    /**
     * 从缓存获取秒杀商品数量
     *
     * @param productId
     * @return
     */
    private Long getSeckillNum(long productId) {
        Long value = null;
        try {
            value = redisUtils.get(SECKILL_NUMBER_KEY_PREFIX + productId);
        } catch (Exception e) {
            log.error("从缓存获取秒杀商品[{}]数量异常", productId, e);
        }
        return value;
    }

    /**
     * 缓存秒杀商品数量
     *
     * @param productId
     */
    public void cacheSeckillNum(long productId) {
        final String lockKey = SECKILL_INIT_NUMBER_CACHE_LOCK_KEY_PREFIX + productId;
        final String lockValue = IdUtils.uuid();
        // 使用分布式锁锁控制库存缓存操作
        distributedLock.executeWithLock(c -> {
            // 从数据库获取商品数量
            long productNum = orderRpcService.getStockNum(productId);
            // 设置缓存
            redisUtils.set(SECKILL_NUMBER_KEY_PREFIX + productId, productNum);
        }, lockKey, lockValue, 3, TimeUnit.SECONDS);
    }

    /**
     * 缓存秒杀结果
     *
     * @param productId
     * @param userId
     * @param fshopRet
     */
    public void cacheSeckillResult(long productId, long userId, SeckillRetBO fshopRet) {
        redisUtils.hashPut(SECKILL_RESULT_KEY_PREFIX + productId, userId, fshopRet);
    }

    /**
     * 获取秒杀结果
     *
     * @param productId
     * @param userId
     * @return
     */
    public SeckillRetBO getSeckillResult(long productId, long userId) {
        return redisUtils.hashGet(SECKILL_RESULT_KEY_PREFIX + productId, userId);
    }

    /**
     * 缓存秒杀验证码
     *
     * @param userId
     * @param verifyCode
     */
    public void cacheVerifyCode(long userId, String verifyCode) {
        redisUtils.set(SECKILL_VERIFY_CODE_KEY_PREFIX + userId, verifyCode, 60, TimeUnit.SECONDS);
    }

    /**
     * 获取秒杀验证码
     *
     * @param userId
     */
    public String getVerifyCode(long userId) {
        return redisUtils.get(SECKILL_VERIFY_CODE_KEY_PREFIX + userId);
    }

    /**
     * 缓存秒杀path
     *
     * @param userId
     * @param productId
     * @param pathId
     */
    public void cacheSeckillPath(long userId, long productId, String pathId) {
        redisUtils.set(SECKILL_PATH_KEY_PREFIX + userId + "_" + productId, pathId, 60, TimeUnit.SECONDS);
    }

    /**
     * 获取秒杀path
     *
     * @param userId
     * @param productId
     * @return
     */
    public String getSeckillPath(long userId, long productId) {
        return redisUtils.get(SECKILL_PATH_KEY_PREFIX + userId + "_" + productId);
    }

}
