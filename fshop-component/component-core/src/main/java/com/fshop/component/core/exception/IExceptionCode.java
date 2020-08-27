/*
 * Copyright [2020] [rannuo]
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
package com.fshop.component.core.exception;

/**
 * Description: 异常错误枚举编码接口
 * 定义了异常枚举数据访问接口。自定义异常枚举类都需要实现此接口，以适应全局异常处理
 *
 * @author 然诺
 * @date 2020/2/22
 */
public interface IExceptionCode {

    /**
     * 获取状态码
     *
     * @return 状态码：包含基础异常码和自定义码
     */
    int getStatus();

    /**
	 * 获取异常消息
	 *
	 * @return 异常提示
	 */
	String getMessage();

    /**
     * 获取编码
     *
     * @return 编码枚举名称
     */
    String getCode();
}
