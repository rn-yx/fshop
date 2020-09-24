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
package com.fshop.component.rabbitmq.data;

/**
 * Description: MQ常量
 *
 * @author 然诺
 * @date 2020/2/22
 */
public class MQConstants {
	/**
	 * 你的业务交换机名称
	 */
	public static final String BUSINESS_EXCHANGE = "business.exchange";
	/**
	 * 你的业务队列名称
	 */
	public static final String BUSINESS_QUEUE = "business.mq";
	/**
	 * 你的业务key
	 */
	public static final String BUSINESS_KEY = "business.key";


	public static final String MQ_PRODUCER_RETRY_KEY = "mq.producer.retry.key";
	public static final String MQ_CONSUMER_RETRY_COUNT_KEY = "mq.consumer.retry.count.key";

	/**
	 * 死信队列配置
	 */
	public static final String DLX_EXCHANGE = "dlx.exchange";
	public static final String DLX_QUEUE = "dlx.mq";
	public static final String DLX_ROUTING_KEY = "dlx.routing.key";
}
