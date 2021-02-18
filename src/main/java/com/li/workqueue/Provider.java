package com.li.workqueue;

import com.li.utils.MqFactoryUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/15 17:56
 * 工作队列  一个生产者 对应多个消费者的那种
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = MqFactoryUtil.getConnection();
        //获取通道对象
        Channel channel = connection.createChannel();

        //声明队列
       channel.queueDeclare("work", true, false, false, null);

        //生产消息
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish("", "work", null, ("我是工作消息队列"+(i+1)).getBytes());
            //System.out.println(i);
        }

        //关闭资源
        MqFactoryUtil.closeConnectAndChannel(channel, connection);

    }
}
