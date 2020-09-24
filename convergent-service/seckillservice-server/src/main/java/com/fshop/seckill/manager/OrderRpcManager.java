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
package com.fshop.seckill.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fshop.order.rpc.OrderRpcService;
import com.fshop.order.rpc.OrderRpcServiceFallback;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: 订单RPC调用服务
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
@Service
public class OrderRpcManager extends OrderRpcServiceFallback {

    @Reference(version = "1.0.0", check = false, timeout = 5000, retries = 2, loadbalance = "leastActive")
    private OrderRpcService orderRpcService;

    @HystrixCommand(fallbackMethod = "getProductStockNum")
    public int getStockNum(long productId) {
        return orderRpcService.getProductStockNum(productId);
    }

}
