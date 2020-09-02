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
 * Description: 用户详情表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class UserDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户详情ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 级别：1 普通VIP，2 超级VIP
     */
    private Integer levelId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 证件类型：1 身份证，2 军官证，3 护照
     */
    private Integer certificateType;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 手机号
     */
    private Integer phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
