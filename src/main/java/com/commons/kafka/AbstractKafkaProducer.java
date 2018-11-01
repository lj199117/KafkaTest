package com.commons.kafka;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * kafka 生产者服务
 * 使用者实现该接口类，并需要发布@service 服务
 * 
 * @author Hebing
 * @since 2018年10月12日
 */
public abstract class AbstractKafkaProducer {

	private static final Logger logger = LoggerFactory.getLogger(AbstractKafkaProducer.class);

	private Producer<String, String> producer;

	private int[] partions;

	@PostConstruct
	public void init() {
		if (producer == null) {
			producer = new KafkaProducer<>(KafkaConfig.getProducer(getKafkaServer()));
			logger.info("【kafka】kafka生产者配置");
		}
		// 获取该主题下面所有partions
		List<PartitionInfo> partitionsFor = producer.partitionsFor(getTopics());
		partions = new int[partitionsFor.size()];
		for (int i = 0; i < partitionsFor.size(); i++) {
			partions[i] = partitionsFor.get(i).partition();
		}
	}

	@PreDestroy
	public void preDestroy() {
		producer.close();
	}

	/**
	 * 生产消息
	 * 
	 * @param message
	 */
	public void produceMessage(String message) {
		try {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(getTopics(), message);
			producer.send(record, new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					logger.debug("【kafka】发布消息 partition:{} ,offset:{} 成功", metadata.partition(), metadata.offset());
				}
			});
		} catch (Exception e) {
			logger.error("【kafka】生产消息异常", e);
			throw e;
		}
	}

	/**
	 * 消息生产
	 * 
	 * @param to 目标，用户ID或者群组ID
	 * @param key
	 * @param message
	 */
	public void produceMessage(Long to, String key, String message) {
		try {
			int partition = (int) (to % partions.length);
			partition = partions[partition];
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(getTopics(), partition, key, message);
			producer.send(record, new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					logger.debug("【kafka】发布消息 partition:{} ,offset:{} 成功", metadata.partition(), metadata.offset());
				}
			});
		} catch (Exception e) {
			logger.error("【kafka】生产消息异常", e);
			throw e;
		}
	}

	/**
	 * 获取kafka服务器地址
	 * 
	 * @return 192.168.60.191:9092,192.168.60.192:9092
	 */
	protected abstract String getKafkaServer();

	/**
	 * 获取消息topics
	 * 
	 * @return 例：user-createAccount-topics
	 */
	protected abstract String getTopics();

}
