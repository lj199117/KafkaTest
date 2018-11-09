/**
 * 北京钉图互动科技 all right reserver
 */
package com.kafka.prod.testqyx;

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
public class KafkaImGiftRecConsumer extends AbstractKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaImGiftRecConsumer.class);

	@Value("${kafka.serverlist}")
	private String kafkaServer;

	@Override
	protected String getKafkaServer() {
		return kafkaServer;
	}

	@Override
	protected String getTopics() {
		return "timeline-giftReceive-topics";
	}

	@Override
	protected String getGroupId() {
		return "groupId-live-test";
	}

	@Override
	protected void process(List<ConsumerMessage> messages) {
		for (ConsumerMessage message : messages) {
			logger.info("#########GiftItemGiveMsgHandle handle########,msg {}", message);
		}
	}

	@Override
	protected Boolean consumeLast() {
		return false;
	}

}
