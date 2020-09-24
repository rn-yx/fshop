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
 * Description: 物流公司表VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@ApiModel(value="Shipping对象VO", description="物流公司表视图对象")
public class ShippingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物流公司ID")
    private Long id;

    @ApiModelProperty(value = "物流公司名称")
    private String companyName;

    @ApiModelProperty(value = "物流公司联系人")
    private String companyContact;

    @ApiModelProperty(value = "物流公司联系电话")
    private String companyPhone;

    @ApiModelProperty(value = "配送价格")
    private BigDecimal deliveryPrice;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;


}
