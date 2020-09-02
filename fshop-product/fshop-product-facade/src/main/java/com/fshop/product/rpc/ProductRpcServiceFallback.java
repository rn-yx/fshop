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
package com.fshop.product.rpc;

import com.fshop.product.model.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 订单RPC回调
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Slf4j
public class ProductRpcServiceFallback implements ProductRpcService {

    @Override
    public List<ProductDTO> getOnShelfProducts(int productStatus) {
        log.info("获取已商品[状态：{}]触发熔断回调", productStatus);
        return new ArrayList<>(0);
    }

}
