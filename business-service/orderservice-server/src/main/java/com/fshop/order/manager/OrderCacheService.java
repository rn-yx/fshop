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
package com.fshop.order.manager;

import com.fshop.component.redis.RedisUtils;
import com.fshop.order.model.bo.SeckillRetBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Resource
    private RedisUtils redisUtils;

    /**
     * 设置秒杀结果
     *
     * @param productId
     * @param userId
     * @param fshopRet
     */
    public void cacheSeckillResult(long productId, long userId, SeckillRetBO fshopRet) {
        redisUtils.hashPut(SECKILL_RESULT_KEY_PREFIX + productId, userId, fshopRet);
    }

}
