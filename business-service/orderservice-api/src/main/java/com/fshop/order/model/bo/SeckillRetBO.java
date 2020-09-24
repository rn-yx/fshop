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
package com.fshop.order.model.bo;

import com.fshop.order.constant.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Description: 秒杀结果BO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "秒杀结果BO")
public class SeckillRetBO {
    @ApiModelProperty(value = "秒杀单号", required = true)
    private String seckillSn;
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;
    @ApiModelProperty(value = "商品ID", required = true)
    private Long productId;
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderSn;
    @ApiModelProperty(value = "订单状态", required = true)
    private Integer orderStatus;

    public static SeckillRetBO build(long userId, long productId, OrderStatus orderStatus) {
        SeckillRetBO fshopRet = new SeckillRetBO();
        fshopRet.setUserId(userId);
        fshopRet.setProductId(productId);
        fshopRet.setOrderStatus(orderStatus.getStatus());
        return fshopRet;
    }

}
