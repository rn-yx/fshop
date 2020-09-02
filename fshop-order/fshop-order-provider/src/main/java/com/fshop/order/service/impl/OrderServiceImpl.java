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
package com.fshop.order.service.impl;

import com.fshop.component.mysql.TransactionUtils;
import com.fshop.component.redis.RedisDistributedLock;
import com.fshop.component.utils.BigDecimalUtils;
import com.fshop.component.utils.IdUtils;
import com.fshop.component.utils.SnowFlakeUtils;
import com.fshop.order.constant.OrderStatus;
import com.fshop.order.entity.OrderMaster;
import com.fshop.order.entity.WarehouseStock;
import com.fshop.order.manager.OrderCacheService;
import com.fshop.order.mapper.OrderMasterMapper;
import com.fshop.order.mapper.WarehouseStockMapper;
import com.fshop.order.model.bo.SeckillRetBO;
import com.fshop.order.model.query.OrderCreateQuery;
import com.fshop.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.fshop.order.constant.OrderConstants.SECKILL_DECREASE_STOCK_LOCK_KEY_PREFIX;

/**
 * Description: 订单处理器
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMasterMapper orderMasterMapper;
    @Resource
    private WarehouseStockMapper warehouseStockMapper;
    @Autowired
    private OrderCacheService orderCacheService;
    @Autowired
    private TransactionUtils transactionUtils;
    @Autowired
    private RedisDistributedLock distributedLock;

    @Override
    public void createOrder(OrderCreateQuery orderCreateQuery) {
        long productId = orderCreateQuery.getOrderItem().getProductId();
        // 判断数据库的库存
        long stockNum = warehouseStockMapper.selectStockNumByProductId(productId);
        if (stockNum <= 0) {
            log.info("用户[{}]秒杀的商品[{}]已售完", orderCreateQuery.getUserId(), productId);
            return;
        }

        // 判断是否重复秒杀
        boolean isRepeat = orderMasterMapper.checkByUserIdAndProductId(orderCreateQuery.getUserId(), productId);
        if (isRepeat) {
            log.info("用户[{}]已秒杀过商品[{}]", orderCreateQuery.getUserId(), productId);
            return;
        }

        Pair<OrderMaster, WarehouseStock> orderPair = buildOrder(orderCreateQuery);
        SeckillRetBO seckillRet = SeckillRetBO.build(orderCreateQuery.getUserId(), productId, OrderStatus.SUCCESS);
        seckillRet.setOrderSn(orderPair.getLeft().getOrderSn());
        seckillRet.setSeckillSn(orderCreateQuery.getSeckillSn());
        final String lockKey = SECKILL_DECREASE_STOCK_LOCK_KEY_PREFIX + productId;
        final String lockValue = IdUtils.uuid();
        // 执行秒杀事务
        transactionUtils.transact(c -> {
            // 扣减库存（使用分布式锁）
            distributedLock.executeWithLock(e -> warehouseStockMapper.deductStockNum(productId,
                    orderPair.getRight().getLockNum()), lockKey, lockValue, 3, TimeUnit.SECONDS);
            // 生成数据库订单记录
            orderMasterMapper.insert(orderPair.getLeft());
            // 设缓存秒杀下单结果
            orderCacheService.cacheSeckillResult(productId, orderCreateQuery.getUserId(), seckillRet);
        });
    }

    private static Pair<OrderMaster, WarehouseStock> buildOrder(OrderCreateQuery orderCreateQuery) {
        OrderCreateQuery.OrderItem orderItem = orderCreateQuery.getOrderItem();
        WarehouseStock warehouseStock = new WarehouseStock();
        warehouseStock.setProductId(orderItem.getProductId());
        warehouseStock.setLockNum(orderItem.getProductNum());
        OrderMaster order = new OrderMaster();
        order.setOrderSn(String.valueOf(SnowFlakeUtils.uniqueId()));
        order.setUserId(orderCreateQuery.getUserId());
        order.setOrderAmount(BigDecimalUtils.multiply(BigDecimal.valueOf(orderItem.getProductNum()), orderItem.getProductPrice()));
        return Pair.of(order, warehouseStock);
    }

}
