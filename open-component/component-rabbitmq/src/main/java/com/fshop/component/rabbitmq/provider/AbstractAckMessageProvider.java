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
package com.fshop.component.rabbitmq.provider;

import com.fshop.component.rabbitmq.data.RabbitmqMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: RabbitMQ消息Provider
 * 问题：当消息的生产者将消息发送出去之后，消息到底有没有正确地到达服务器呢?
 * RabbitMQ 针对这个问题，提供了两种解决方式:
 * 1）通过事务机制实现；
 * 2）通过发送方确认(publisher confirm)机制实现。
 * 相比之下，发送方确认机制最大的好处在于它是异步的
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Slf4j
public abstract class AbstractAckMessageProvider implements RabbitTemplate.ConfirmCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private Jackson2JsonMessageConverter messageConverter;

	/**
	 * 发送消息
	 */
	protected void send(RabbitmqMetaMessage metaMessage) {
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setMessageConverter(messageConverter);
		rabbitTemplate.convertAndSend(metaMessage.getExchange(), metaMessage.getRoutingKey(),
				metaMessage.getPayload(), new CorrelationData(metaMessage.getMessageId()));
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("confirm -> 消息id:" + correlationData.getId());
		// 只要消息能投入正确的交换器中，ack就为true
		if (ack) {
			log.info("消息发送确认成功");
		} else {
			log.error("消息发送确认失败:{}", cause);
			// 这里可以采用重试机制
		}
	}

}
