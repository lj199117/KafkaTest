/**
 * 北京钉图互动科技 all right reserver
 */
package com.kafka.prod;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.commons.kafka.AbstractKafkaConsumer;
import com.commons.kafka.model.ConsumerMessage;


/**
 */
@Component
public class KafkaImLogoutConsumer extends AbstractKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaImLogoutConsumer.class);

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

	@Override
	protected String getGroupId() {
		return "test-groupId-imService"; // 组名相同的消费者只能被消费一次
	}

	@Override
	protected void process(List<ConsumerMessage> messages) {
		for (ConsumerMessage message : messages) {
			logger.debug("收到离线kafka消息: {}", message.getMessage());
			System.out.println(message);
		}
	}

	@Override
	protected Boolean consumeLast() {
		return false;
	}

}
