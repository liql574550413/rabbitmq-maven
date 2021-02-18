package com.li.ql;

import com.li.utils.MqFactoryUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/14 21:52
 * 测试 消费者 ,
 * 不能犯的低级错误：
 * 消费者的线程需要监听消息队列 所以需要一个长久存活的线程，不能使用Test线程
 * 不然会被杀死。
 * 生产者和消费者的参数要严格对应，如持久化的参数等必须一致
 */
public class Customer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //通过工厂获取连接  管道
        Channel channel = MqFactoryUtil.getConnection().createChannel();

        //绑定通道对象
        channel.queueDeclare("hello",true,false,false,null);

        //消费消息
        //参数1:消费那个队列的消息队列名称
        // 参数2:开始消息的自动确认机制,默认为true 是消费者向mq确认消息已经被消费了。
                //当消费者刚收到消息后 消息队列就会从队列中把这个消息删除，所以
                //开启自动确认 可以会导致任务丢失（未处理成功就宕机）
                //如果开启不自动确认 需要制定每次接收几个消息。channel.basicQos(int no) 且还需要手动确认
       // channel.basicQos(1);
        //参数3:消费时的回调接口
        String s = channel.basicConsume("hello", true, new DefaultConsumer(channel){
            //重写方法的快捷键 ctrl+o

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者消费的消息："+ new String(body));
            }
        });


        //如果不希望一直被监听 可以手动关闭, 但是可能导致消息没处理完就结束了
       // channel.close();
      //  connection.close();

    }
}
