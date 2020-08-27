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
package com.fshop.order.mq;

import com.fshop.component.rabbitmq.consumer.AbstractMessageConsumer;
import com.fshop.component.utils.JsonUtils;
import com.fshop.order.model.query.OrderCreateQuery;
import com.fshop.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.fshop.component.rabbitmq.RabbitmqConfig.ORDER_QUEUE;

/**
 * Description: 派单服务-消费者
 * 消费者若成功的消费了消息，同样会给消息队列返回一个消费成功的确认消息。一旦有消费者成功注册到相应的消息服务，
 * 消息将会被消息服务通过basic.deliver推（push）给消费者，此时消息会包含一个deliver tag用来唯一的标识消息。
 * 如果此时是手动模式，就需要手动的确认消息已经被成功消费，否则消息服务将会重发消息（因为消息已经持久化到了硬盘上，
 * 所以无论消息服务是不是可能挂掉，都会重发消息）。而且必须确认，无论是成功或者失败，否则会引起非常严重的问题
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Slf4j
@Component
public class OrderConsumer extends AbstractMessageConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = ORDER_QUEUE)
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        super.onMessage(message, channel);
    }

    @Override
    public void receiveMessage(Message message, MessageConverter messageConverter) {
        // 解析订单数据
        OrderCreateQuery createQuery = (OrderCreateQuery) messageConverter.fromMessage(message);
        log.info("接收到秒杀单号为[{}]的订单消息：{}", createQuery.getSeckillSn(), JsonUtils.toJson(createQuery));

        // 创建订单
        orderService.createOrder(createQuery);
        log.info("秒杀单号为[{}]的订单创建成", createQuery.getSeckillSn());
    }

}
