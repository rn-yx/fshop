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
package com.fshop.component.core.constant;

/**
 * Description: 全局常量类
 *
 * @author 然诺
 * @date 2020/2/22
 */
public class GlobalConstants {
    /**
     * 接口调用失败
     */
    public static final  int FAIL = 0;
    /**
     * 接口调用成功
     */
    public static final  int SUCCESS = 1;
    /**
     * 接口调用成功提示
     */
    public static final String SUCCESS_MSG = "操作成功";
    /**
     * 接口调用失败提示
     */
    public static final String FAIL_MSG = "操作失败";
    /**
     * 日志分隔符
     */
    public static final String LOG_SEP = "\t";
	/**
	 * 树结构根
	 */
    public static final int ROOT = 0;

    /**
     * 订单队列
     */
    public static final String ORDER_QUEUE = "order-mq";

}
