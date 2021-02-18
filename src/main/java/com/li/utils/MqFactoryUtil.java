package com.li.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.corba.se.pept.transport.ConnectionCache;
import com.sun.org.apache.bcel.internal.generic.IfInstruction;
import jdk.nashorn.internal.ir.IfNode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liqiuliang
 * @version 1.0
 * @date 2021/2/15 9:52
 */
public class MqFactoryUtil {
    //创建工厂是一个重量级操作 所以放在静态代码块中
    private static ConnectionFactory connectionFactory;
    static {
        //获取连接工chang
         connectionFactory = new ConnectionFactory();
        //设置主机 端口
        connectionFactory.setHost("192.168.126.130");
        connectionFactory.setPort(5672);
        //设置用户名 密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        //设置rabbitmq 的虚拟主机
        connectionFactory.setVirtualHost("ems");
    }

    public static Connection getConnection() {


        //通过工厂获取连接
        Connection connection=null;

        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;

        //获取管道
     /*   Channel channel = connection.createChannel();
        return channel;*/
    }

    //关闭连接和通道的方法
    public static void closeConnectAndChannel(Channel channel, Connection connection){
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
