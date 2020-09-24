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
package com.fshop.order.model.query;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description: 订单事件消息
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Data
public class OrderCreateQuery implements Serializable {
	/**
	 * 秒杀单号
	 */
	private String seckillSn;
	/**
	 * 用户ID
	 */
	private Long userId;
    /**
     * 订单所含物品
     */
    private OrderItem orderItem;

    @Getter
	@Setter
	public static class OrderItem {
        /**
         * 商品ID
         */
        private Long productId;
        /**
         * 商品数量
         */
        private Integer productNum;
		/**
		 * 商品价格
		 */
		private BigDecimal productPrice;
    }

}
