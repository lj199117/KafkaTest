package com.kafka.prod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.commons.kafka.AbstractKafkaProducer;


/**
 * 登陆kafka消息 生产类
 */
@Component
public class KafkaImLoginProducer extends AbstractKafkaProducer {

	@Value("${kafka.serverlist}")
	private String kafkaServer;

	@Override
	protected String getKafkaServer() {
		return kafkaServer;
	}

	@Override
	protected String getTopics() {
		return "test-kafka-login-topics";
	}

}
