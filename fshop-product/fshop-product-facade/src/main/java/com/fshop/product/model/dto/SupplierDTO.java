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
import java.time.LocalDateTime;

/**
 * Description: 供应商表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class SupplierDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private Long id;

    /**
     * 编码
     */
    private String supplierCode;

    /**
     * 名称
     */
    private String supplierName;

    /**
     * 类型：1自营，2平台
     */
    private Integer supplierType;

    /**
     * 状态：0禁止，1启用
     */
    private Integer supplierStatus;

    /**
     * 联系人
     */
    private String supplierContact;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 开户银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 地址
     */
    private String address;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
