package com.activeMq.spring.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service("topic")
public class TopicProducerServiceImpl implements ProducerService {
 
    @Resource
    private JmsTemplate jmsTopicTemplate;
    
    @Override
    public void sendMessage(Destination destination, final String message) {
    	jmsTopicTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    } 
    
    @Override
    public void sendMessage(String destinationName, final String message) {
    	jmsTopicTemplate.send(destinationName, new MessageCreator() {
    		public Message createMessage(Session session) throws JMSException {
    			return session.createTextMessage(message);
    		}
    	});
    } 
}