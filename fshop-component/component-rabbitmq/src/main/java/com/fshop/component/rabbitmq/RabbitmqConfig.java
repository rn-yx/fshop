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
package com.fshop.component.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: RabbitMQ配置
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Configuration
public class RabbitmqConfig {
	/**
	 * 下单队列
	 */
	public static final String ORDER_QUEUE = "order_queue";
	/**
	 * 下单交换机
	 */
	public static final String ORDER_EXCHANGE_NAME = "order_exchange";
	/**
	 * 下单路由KEY
	 */
	public static final String ORDER_ROUTING_KEY = "orderRoutingKey";

	/**
	 * 定义下单队列
	 */
	@Bean
	public Queue directOrderQueue() {
		return new Queue(ORDER_QUEUE);
	}

	/**
	 * 定义下单交换机
	 */
	@Bean
	DirectExchange directOrderExchange() {
		return new DirectExchange(ORDER_EXCHANGE_NAME);
	}

	/**
	 * 订单队列与交换机绑定
	 */
	@Bean
	Binding bindingExchangeOrderDicQueue() {
		return BindingBuilder.bind(directOrderQueue()).to(directOrderExchange()).with(ORDER_ROUTING_KEY);
	}

	/**
	 * 消息JSON转换器
	 */
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
