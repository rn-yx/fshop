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
package com.fshop.user.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description: 余额日志表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class BalanceLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 余额日志ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 记录来源：1 订单，2 退货单
     */
    private Integer source;

    /**
     * 相关单据ID
     */
    private Long sourceId;

    /**
     * 变动金额
     */
    private BigDecimal changeAmount;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
