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
package com.fshop.product.constant;

/**
 * Description: 商品状态枚举
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public enum ProductStatus {
    /**
     * 未审核
     */
    UNAUDITED(0),
    /**
     * 已审核
     */
    AUDITED(1),
    /**
     * 下架
     */
    OFF_SHELF(2),
    /**
     * 上架
     */
    ON_SHELF(3);

    private int status;

    ProductStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
