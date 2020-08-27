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
package com.fshop.rest.service.impl;

import com.fshop.component.core.exception.GlobalExCode;
import com.fshop.component.core.exception.Preconditions;
import com.fshop.component.utils.IdUtils;
import com.fshop.component.utils.SnowFlakeUtils;
import com.fshop.order.constant.OrderStatus;
import com.fshop.order.model.bo.SeckillRetBO;
import com.fshop.order.model.query.OrderCreateQuery;
import com.fshop.rest.manager.OrderCacheService;
import com.fshop.rest.model.query.SeckillQuery;
import com.fshop.rest.mq.OrderMqProducer;
import com.fshop.rest.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description：订单服务接口实现类
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private OrderMqProducer orderMqProducer;
    @Autowired
    private OrderCacheService orderCacheService;

    /**
     * 内存标识Map（避免频繁访问缓存）
     */
    private static Map<String, Boolean> MEMORY_MARK_MAP = new ConcurrentHashMap<>();

    @Override
    public SeckillRetBO seckill(SeckillQuery fshopQuery) {
        // 判断内存标识中商品是否售罄
        Boolean mark = MEMORY_MARK_MAP.get(fshopQuery.getProductId().toString());
        if (mark != null && mark) {
            return SeckillRetBO.build(fshopQuery.getUserId(), fshopQuery.getProductId(), OrderStatus.SOLD_OUT);
        }

        // 判断是否重复秒杀
        SeckillRetBO seckillRet = orderCacheService.getSeckillResult(fshopQuery.getProductId(), fshopQuery.getUserId());
        if (seckillRet != null) {
            return SeckillRetBO.build(fshopQuery.getUserId(), fshopQuery.getProductId(), OrderStatus.REPEAT);
        }

        // 预减库存
        Long stock = orderCacheService.deductStock(fshopQuery.getProductId(), fshopQuery.getProductNum());
        if (stock == null || stock <= 0) {
            // 设置售罄内存标识
            MEMORY_MARK_MAP.put(fshopQuery.getProductId().toString(), true);
            return SeckillRetBO.build(fshopQuery.getUserId(), fshopQuery.getProductId(), OrderStatus.SOLD_OUT);
        }

        // 发送到队列，流量削峰和异步处理
        OrderCreateQuery orderCreateQuery = buildOrderCreateQuery(fshopQuery);
        orderMqProducer.sendOrderToMq(orderCreateQuery);

        // 缓存秒杀下单结果，以便客户端轮询获取
        seckillRet = SeckillRetBO.build(fshopQuery.getUserId(), fshopQuery.getProductId(), OrderStatus.PENDING);
        seckillRet.setSeckillSn(orderCreateQuery.getSeckillSn());
        orderCacheService.cacheSeckillResult(fshopQuery.getProductId(), fshopQuery.getUserId(), seckillRet);
        return seckillRet;
    }

    @Override
    public SeckillRetBO getSeckillRet(long userId, long productId) {
        return orderCacheService.getSeckillResult(productId, userId);
    }

    @Override
    public String getVerifyCode(long userId) {
        // 生成6位验证码
        String verifyCode = RandomStringUtils.randomNumeric(6);
        // 将验证码缓存
        orderCacheService.cacheVerifyCode(userId, verifyCode);
        return verifyCode;
    }

    @Override
    public boolean checkVerifyCode(long userId, String verifyCode) {
        String verifyCodeInCache = orderCacheService.getVerifyCode(userId);
        Preconditions.checkTrue(StringUtils.isNotBlank(verifyCodeInCache), GlobalExCode.VERIFY_CODE_EXPIRED);
        // 这里验证码错误时，不建议直接抛异常（触发异常处理机制，开销较大，尽量避免）
        return verifyCodeInCache.equals(verifyCode);
    }

    @Override
    public String getSeckillPath(long userId, long productId) {
        // 生成随机数（MD5）作为pathId
        String pathId = DigestUtils.md5DigestAsHex(IdUtils.uuid().getBytes());
        // 缓存pathId
        orderCacheService.cacheSeckillPath(userId, productId, pathId);
        return pathId;
    }

    @Override
    public boolean verifyPath(long userId, long productId, String pathId) {
        String pathIdInCache = orderCacheService.getSeckillPath(userId, productId);
        return StringUtils.isNotBlank(pathIdInCache) && pathIdInCache.equals(pathId);
    }


    private OrderCreateQuery buildOrderCreateQuery(SeckillQuery placeQuery) {
        OrderCreateQuery.OrderItem orderItem = new OrderCreateQuery.OrderItem();
        orderItem.setProductId(placeQuery.getProductId());
        orderItem.setProductNum(placeQuery.getProductNum());
        orderItem.setProductPrice(BigDecimal.valueOf(1000));
        OrderCreateQuery createQuery = new OrderCreateQuery();
        createQuery.setSeckillSn(SnowFlakeUtils.uniqueIdStr());
        createQuery.setUserId(placeQuery.getUserId());
        createQuery.setOrderItem(orderItem);
        return createQuery;
    }
}
