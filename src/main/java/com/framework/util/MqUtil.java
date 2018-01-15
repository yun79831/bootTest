package com.framework.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by ghost on 2017/11/7.
 */
public class MqUtil {
    private final static Logger logger = Logger.getLogger(MqUtil.class);
    //MQ地址
    private static final String URL = "tcp://192.168.0.1:6666";
    private static final String USER = "";
    private static final String PASSWORD = "";

    @Test
    public void availableTest() {
        assert isMqAvailable();
    }

    @Test
    public void sendMessageTest() {
        String sendMsg = "测试消息";
        assert sendMessage("test", sendMsg);
    }

    //Mq启动检测
    public static boolean isMqAvailable() {
        boolean isAvailable = false;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
        try {
            connection = connectionFactory.createConnection();
            isAvailable = true;
        } catch (JMSException e) {
            logger.error("获取MQ连接失败", e);
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                logger.error("MQ连接关闭异常:", e);
                e.printStackTrace();
            }
        }
        return isAvailable;
    }

    //Mq消息发送
    public boolean sendMessage(String queueName, String sendMsg) {
        boolean result = false;
        logger.info("执行MQ发送消息start:" + sendMsg);
        ConnectionFactory connectionFactory = null;//连接工厂
        Connection connection = null;//连接
        QueueSession session = null;
        Destination destination = null;
        MessageProducer messageProducer = null;
        connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = (QueueSession) connection.createSession(false, 1);
            destination = session.createQueue(queueName);
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(2);
            TextMessage textMessage = session.createTextMessage();
            if (StringUtils.isNotEmpty(sendMsg)) {
                textMessage.setText(sendMsg);
                messageProducer.send(textMessage);
                result = true;
            }
        } catch (JMSException e) {
            e.printStackTrace();
            logger.error("mq消息发送异常:" + e);
        } finally {
            try {
                messageProducer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
                logger.error("mq关闭异常" + e);
            }
        }
        logger.info("执行MQ发送消息end,结果" + result);
        return result;
    }


}
