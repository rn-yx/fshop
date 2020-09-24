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
 * Description: 登陆日志表VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@ApiModel(value="LoginLog对象VO", description="登陆日志表视图对象")
public class LoginLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登陆日志ID")
    private Long loginId;

    @ApiModelProperty(value = "登陆用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户登陆时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "登陆IP")
    private Integer loginIp;

    @ApiModelProperty(value = "登陆类型：0未成功，1成功")
    private Integer loginType;


}
