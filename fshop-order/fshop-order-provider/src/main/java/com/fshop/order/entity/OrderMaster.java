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
package com.fshop.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: 主订单表实体类
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("so_order_master")
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
    private BigDecimal orderAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 支付方式：1现金，2余额，3网银，4支付宝，5微信
     */
    private Integer paymentMethod;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货时间
     */
    private Date receiveTime;

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
    private BigDecimal shippingAmount;

    /**
     * 发货时间
     */
    private Date shippingTime;

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
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}
