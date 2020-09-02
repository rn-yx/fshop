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
package com.fshop.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description: 用户表VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020-08-26
 */
@Data
@ApiModel(value="User对象VO", description="用户表视图对象")
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "登录密码")
    private String loginPwd;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;


}
