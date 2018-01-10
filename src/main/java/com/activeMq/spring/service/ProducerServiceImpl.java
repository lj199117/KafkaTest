package com.activeMq.spring.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {
 
    @Resource
    private JmsTemplate jmsQueueTemplate;
    
    @Override
    public void sendMessage(Destination destination, final String message) {
    	jmsQueueTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    } 
    
    @Override
    public void sendMessage(String destinationName, final String message) {
    	jmsQueueTemplate.send(destinationName, new MessageCreator() {
    		public Message createMessage(Session session) throws JMSException {
    			return session.createTextMessage(message);
    		}
    	});
    } 
}