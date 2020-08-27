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
package com.fshop.rest.mq;

import com.fshop.component.rabbitmq.data.RabbitmqMetaMessage;
import com.fshop.component.rabbitmq.provider.AbstractAckMessageProvider;
import com.fshop.component.utils.JsonUtils;
import com.fshop.order.model.query.OrderCreateQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.fshop.component.rabbitmq.RabbitmqConfig.ORDER_EXCHANGE_NAME;
import static com.fshop.component.rabbitmq.RabbitmqConfig.ORDER_ROUTING_KEY;

/**
 * Description: 订单消息提供者
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Slf4j
@Component
public class OrderMqProducer extends AbstractAckMessageProvider {

    /**
     * 发送创建订单事件消息
     *
     * @param createQuery
     */
    public void sendOrderToMq(OrderCreateQuery createQuery) {
        RabbitmqMetaMessage metaMessage = new RabbitmqMetaMessage();
        metaMessage.setExchange(ORDER_EXCHANGE_NAME);
        metaMessage.setRoutingKey(ORDER_ROUTING_KEY);
        metaMessage.setMessageId(createQuery.getSeckillSn());
        metaMessage.setPayload(createQuery);
        super.send(metaMessage);
        log.info("已发送秒杀单号为[{}]的订单消息：{}", createQuery.getSeckillSn(), JsonUtils.toJson(createQuery));
    }

}
