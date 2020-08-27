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
package com.fshop.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fshop.order.mapper.WarehouseStockMapper;
import com.fshop.order.rpc.OrderRpcService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: 订单 RPC 服务
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Component
@Service(version = "1.0.0", interfaceClass = OrderRpcService.class)
public class OrderRpcServiceImpl implements OrderRpcService {

    @Resource
    private WarehouseStockMapper warehouseStockMapper;

    @HystrixCommand
    @Override
    public int getProductStockNum(long productId) {
        return warehouseStockMapper.selectStockNumByProductId(productId);
    }

}
