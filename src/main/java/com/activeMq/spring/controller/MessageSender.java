package com.activeMq.spring.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
	protected final Logger logger = LogManager.getLogger(getClass());
	private @Autowired JmsTemplate jmsTemplate;
	private @Autowired @Qualifier("infoNewsModifyTopic") Topic infoNewsModifyTopic;

	public void sendModify(final String json) {
		// String json = UtilJson.writeValueAsString0(event);
		jmsTemplate.send(infoNewsModifyTopic, new MessageCreator() {
    		public Message createMessage(Session session) throws JMSException {
    			return session.createTextMessage(json);
    		}/*(session) -> {
			logger.info("发送新闻档案变更通知：{}" + json);
			return session.createTextMessage(json);*/
		});
	}
}
