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
package com.fshop.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description: 商品VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@ApiModel(value="Product对象VO", description="商品视图对象")
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;

    @ApiModelProperty(value = "编码")
    private String productCode;

    @ApiModelProperty(value = "名称")
    private String productName;

    @ApiModelProperty(value = "描述")
    private String productDesc;

    @ApiModelProperty(value = "国条码")
    private String barCode;

    @ApiModelProperty(value = "一级分类ID")
    private Long firstCategoryId;

    @ApiModelProperty(value = "二级分类ID")
    private Long secondCategoryId;

    @ApiModelProperty(value = "三级分类ID")
    private Long thirdCategoryId;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellingPrice;

    @ApiModelProperty(value = "加权平均成本")
    private BigDecimal averageCost;

    @ApiModelProperty(value = "重量")
    private Float weight;

    @ApiModelProperty(value = "长度")
    private Float length;

    @ApiModelProperty(value = "高度")
    private Float height;

    @ApiModelProperty(value = "宽度")
    private Float width;

    @ApiModelProperty(value = "生产日期")
    private LocalDateTime productionDate;

    @ApiModelProperty(value = "保质期")
    private Integer shelfLife;

    @ApiModelProperty(value = "状态：0未审核，1已审核，2下架，3上架")
    private Integer productStatus;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;


}
