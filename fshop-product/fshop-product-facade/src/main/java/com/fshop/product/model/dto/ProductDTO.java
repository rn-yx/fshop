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
package com.fshop.product.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description: 商品DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 编码
     */
    private String productCode;

    /**
     * 名称
     */
    private String productName;

    /**
     * 描述
     */
    private String productDesc;

    /**
     * 国条码
     */
    private String barCode;

    /**
     * 一级分类ID
     */
    private Long firstCategoryId;

    /**
     * 二级分类ID
     */
    private Long secondCategoryId;

    /**
     * 三级分类ID
     */
    private Long thirdCategoryId;

    /**
     * 销售价格
     */
    private BigDecimal sellingPrice;

    /**
     * 加权平均成本
     */
    private BigDecimal averageCost;

    /**
     * 重量
     */
    private Float weight;

    /**
     * 长度
     */
    private Float length;

    /**
     * 高度
     */
    private Float height;

    /**
     * 宽度
     */
    private Float width;

    /**
     * 生产日期
     */
    private LocalDateTime productionDate;

    /**
     * 保质期
     */
    private Integer shelfLife;

    /**
     * 状态：0未审核，1已审核，2下架，3上架
     */
    private Integer productStatus;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
