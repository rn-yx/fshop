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
 * Description: 积分日志表VO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
@ApiModel(value="PointLog对象VO", description="积分日志表视图对象")
public class PointLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "积分日志ID")
    private Long pointId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "积分来源：0订单，1登陆，2活动")
    private Integer source;

    @ApiModelProperty(value = "积分来源相关编号")
    private Integer referNumber;

    @ApiModelProperty(value = "变更积分数")
    private Integer changePoint;

    @ApiModelProperty(value = "积分日志生成时间")
    private LocalDateTime createTime;


}
