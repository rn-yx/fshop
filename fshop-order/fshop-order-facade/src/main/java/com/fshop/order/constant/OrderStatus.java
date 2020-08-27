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
package com.fshop.order.constant;

/**
 * Description：订单状态枚举
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public enum OrderStatus {
    /**
     * 下单中，需要前端轮训状态
     */
    PENDING(1000),
    /**
     * 下单成功
     */
    SUCCESS(1001),
    /**
     * 下单失败
     */
    FAILURE(1002),
    /**
     * 不存在
     */
    NOT_FOUND(1003),
    /**
     * 售罄
     */
    SOLD_OUT(1004),
    /**
     * 重复秒杀
     */
    REPEAT(1005);

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
