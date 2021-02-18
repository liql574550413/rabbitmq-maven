package com.li.workqueue;

import com.li.utils.MqFactoryUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/15 18:09
 * 关闭消息自动确认 autoAck ：false 需要指定每次接收几个，需要开启手动消息确认
 * 这样便可以实现能者多劳。
 */
public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = MqFactoryUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        // 参数2:开始消息的自动确认机制,默认为true 是消费者向mq确认消息已经被消费了。
        //当消费者刚收到消息后 消息队列就会从队列中把这个消息删除，所以
        //开启自动确认 可以会导致任务丢失（未处理成功就宕机）
        //如果开启不自动确认 需要制定每次接收几个消息。channel.basicQos(int no) 且还需要手动确认
       channel.basicQos(1);
        channel.basicConsume("work", false, new DefaultConsumer(channel ){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("我是消息队列-1"+new String(body));
                //开启手动确认
                //参数1 手动确认消息标识 envelope.getDeliveryTag() 队列中具体哪个消息的标识。
                //参数2  置为false代表每次不确认多个，也就是只确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
