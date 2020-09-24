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
package com.fshop.component.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：异常信息对象
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Getter
@Setter
@ApiModel(value = "异常信息对象")
public class ExRet implements Serializable {

	@ApiModelProperty(value = "异常时间", required = true)
	private Date date;

	@ApiModelProperty(value = "异常类名", required = true)
	private String type;

	@ApiModelProperty(value = "异常信息", required = true)
	private String message;

	@ApiModelProperty(value = "异常堆栈", required = true)
	private String stackTrace;

}
