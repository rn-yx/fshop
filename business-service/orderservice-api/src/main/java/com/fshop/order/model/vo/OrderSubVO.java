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
package com.fshop.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description: 子订单表VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@ApiModel(value="OrderSub对象VO", description="子订单表视图对象")
public class OrderSubVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子订单ID")
    private Long id;

    @ApiModelProperty(value = "主订单ID")
    private Long orderMasterId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "买家用户ID")
    private Long userId;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal districtMoney;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal paymentMoney;

    @ApiModelProperty(value = "支付方式：1现金，2余额，3网银，4支付宝，5微信")
    private Integer paymentMethod;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "省")
    private Integer province;

    @ApiModelProperty(value = "市")
    private Integer city;

    @ApiModelProperty(value = "区")
    private Integer district;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "快递公司")
    private String shippingCompany;

    @ApiModelProperty(value = "快递单号")
    private String shippingSn;

    @ApiModelProperty(value = "运费金额")
    private BigDecimal shippingMoney;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime shippingTime;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单积分")
    private Integer orderPoint;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceHead;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;


}
