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
package com.fshop.component.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: 抽象RabbitMQ消息消费者，所有消息消费者必须继承此类
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Slf4j
public abstract class AbstractMessageConsumer {

	@Autowired
	private Jackson2JsonMessageConverter messageConverter;

	/**
	 * 消费次数
	 */
	private Integer maxConsumerCount = 3;

	/**
	 * 接收消息，子类必须实现该方法
	 *
	 * @param message          消息对象
	 * @param messageConverter 消息转换器
	 */
	public abstract void receiveMessage(Message message, MessageConverter messageConverter);

	/**
	 * 处理消息
	 */
	public void onMessage(Message message, Channel channel) throws Exception {
		final MessageProperties messageProperties = message.getMessageProperties();
		log.info("当前消息ID:{}", messageProperties.getMessageId());
		try {
			// 1.1 执行业务逻辑
			receiveMessage(message, messageConverter);
			// 1.2 手动签收消息
			channel.basicAck(messageProperties.getDeliveryTag(), false);
			log.info("手动签收消息,通知mq服务器端删除该消息");
		} catch (Exception e) {
			log.error("RabbitMQ 消息消费失败，" + e.getMessage(), e);
			// 2 丢弃该消息
			channel.basicNack(messageProperties.getDeliveryTag(), false, false);
			/*if (consumerCount >= maxConsumerCount) {
				// 入死信队列
				channel.basicReject(deliveryTag, false);
			} else {
				// 重回到队列，重新消费
				channel.basicNack(deliveryTag, false, true);
			}*/
		}
	}

	public Integer getMaxConsumerCount() {
		return maxConsumerCount;
	}

	public void setMaxConsumerCount(Integer maxConsumerCount) {
		this.maxConsumerCount = maxConsumerCount;
	}
}
