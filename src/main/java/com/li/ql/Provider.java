package com.li.ql;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/14 20:33
 * rabbitmq 生产者
 */
public class Provider {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //创建连接mq的工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机ip
        connectionFactory.setHost("192.168.126.130");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        //设置rabbitmq 的虚拟主机
        connectionFactory.setVirtualHost("ems");

        //通过工厂 获取连接对象
        Connection connection = connectionFactory.newConnection();

        //回去连接中的通道对象
        Channel channel = connection.createChannel();

        //通道绑定对应的消息队列
        //参数1：队列名称，如果没有就自动创建。参数2: 是否持久化  参数3:是否独占队列 参数4:是否自动删除  参数5:其他属性
        //通道绑定对应消息队列
    //参数1 queue:队列名称如果队列不存在自动创建
    //参数2 durable:用来定义队列特性是否要持久化 true持久化队列false 不持久化(与消息吃不持久化无关)
    //参数3 :exclusive 是否独占队列 true独占队列 false 不独占
    //参数4 :autoDelete:是否在消费完成后自动删除队列(当队列中没有消息没有连接时自动删除) true自动删除false 不自动删除,
    // 参数5:额外附加参数
     // 注意：一个通道可以向很多个队列发送消息，在  basicPublish api指定。
    AMQP.Queue.DeclareOk hello =
            channel.queueDeclare("hello", true, false, false, null);

    //发布消息
        //参数1；exchange 交换机 ,没有就空着
        //routingKey: 队列的名称
        //参数3 props ：发布消息时的额外一些属性。如持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
        //参数4 ：发布消息的具体内容 要求字节类型。
        for (int i = 0; i <100 ; i++) {
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello,i love you :"+ (i+1)).getBytes());
        }

        //关闭连接
        channel.close();
        connection.close();

    }

}
