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
import java.time.LocalDateTime;

/**
 * Description: 仓库表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class WarehouseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    private Long id;

    /**
     * 仓库编码
     */
    private String warehouseSn;

    /**
     * 仓库名称
     */
    private String warehoustName;

    /**
     * 仓库联系人
     */
    private String warehouseContact;

    /**
     * 仓库电话
     */
    private String warehousePhone;

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
    private Integer distrct;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 仓库状态：0禁用，1启用
     */
    private Integer warehouseStatus;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
