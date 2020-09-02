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
import java.time.LocalDateTime;

/**
 * Description: 用户级别表DTO
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Data
public class LevelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 级别ID
     */
    private Long id;

    /**
     * 级别名称
     */
    private String levelName;

    /**
     * 最低积分
     */
    private Integer minPoint;

    /**
     * 最高积分
     */
    private Integer maxPoint;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;


}
