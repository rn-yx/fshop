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
package com.fshop.order.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description: 子订单表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class OrderSubDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 子订单ID
     */
    private Long id;

    /**
     * 主订单ID
     */
    private Long orderMasterId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 买家用户ID
     */
    private Long userId;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 优惠金额
     */
    private BigDecimal districtMoney;

    /**
     * 支付金额
     */
    private BigDecimal paymentMoney;

    /**
     * 支付方式：1现金，2余额，3网银，4支付宝，5微信
     */
    private Integer paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 省
     */
    private Integer province;

    /**
     * 市
     */
    private Integer city;

    /**
     * 区
     */
    private Integer district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 快递公司
     */
    private String shippingCompany;

    /**
     * 快递单号
     */
    private String shippingSn;

    /**
     * 运费金额
     */
    private BigDecimal shippingMoney;

    /**
     * 发货时间
     */
    private LocalDateTime shippingTime;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单积分
     */
    private Integer orderPoint;

    /**
     * 发票抬头
     */
    private String invoiceHead;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
