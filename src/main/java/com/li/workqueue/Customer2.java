package com.li.workqueue;

import com.li.utils.MqFactoryUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/15 18:09
 */
public class Customer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = MqFactoryUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", true, new DefaultConsumer(channel ){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("我是消息队列-2"+new String(body));
            }
        });

    }
}
