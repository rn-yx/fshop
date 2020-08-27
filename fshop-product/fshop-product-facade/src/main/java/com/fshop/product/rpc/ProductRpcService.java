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
package com.fshop.product.rpc;

import java.util.List;

/**
 * Description: 商品 RPC 服务
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public interface ProductRpcService {

    /**
     * 获取已上架商品列表
     *
     * @param productStatus
     * @return
     */
    List<ProductDTO> getOnShelfProducts(int productStatus);

}
