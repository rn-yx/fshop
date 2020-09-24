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
package com.fshop.order.rpc;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 订单RPC回调
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
public class OrderRpcServiceFallback implements OrderRpcService {

    @Override
    public int getProductStockNum(long productId) {
        log.info("获取商品[{}]库存触发熔断回调", productId);
        return 0;
    }

}
