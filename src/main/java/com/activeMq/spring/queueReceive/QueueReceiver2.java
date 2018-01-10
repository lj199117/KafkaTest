package com.activeMq.spring.queueReceive;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
@Component
public class QueueReceiver2 implements MessageListener {
	 
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        try {
            System.out.println("QueueReceiver2接收者收到消息：" + textMsg.getText());
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
 
}
