/**
 * 北京钉图互动科技 all right reserver
 */
package com.commons.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.kafka.model.ConsumerMessage;

/**
 * kafka 消费服务，同步commit offset
 * 使用者实现该接口类，并需要发布@service 服务
 * 
 * @author Hebing
 * @since 2018年10月12日
 */
public abstract class AbstractKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);

	private KafkaConsumer<String, String> consumer;

	private ExecutorService executorService;

	private boolean isStop = false;

	@PostConstruct
	public void init() {
		if (consumer == null) {
			consumer = new KafkaConsumer<>(KafkaConfig.getConsumer(getKafkaServer(), getGroupId()));
			consumer.subscribe(Arrays.asList(getTopics()));
			logger.info("【kafka】kafka生产者配置");
		}
		// 开启消费消息线程
		isStop = false;
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
			public void run() {
				consumer();
			}
		});
	}

	private void consumer() {
		dealConsumerLast();
		while (!isStop) {
			try {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				if (records != null && records.count() > 0) {
					List<ConsumerMessage> messages = new ArrayList<>(records.count());
					for (ConsumerRecord<String, String> record : records) {
						logger.debug("【kafka】消费消息 partition:{} ,offset:{},key: {} ,message:{}", record.partition(), record.offset(), record.key(),
								record.value());
						ConsumerMessage consumerMessage = new ConsumerMessage();
						consumerMessage.setKey(record.key());
						consumerMessage.setMessage(record.value());
						messages.add(consumerMessage);
					}
					try {
						process(messages);
					} catch (Exception e) {
						logger.error("【kafka】消费消息异常", e);
					}
					continue;
				}
			} catch (Exception e) {
				logger.error("【kafka】消费消息异常", e);
			} finally {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					logger.error("【kafka】消费消息异常", e);
				}
			}
		}
	}

	private void dealConsumerLast() {
		if (consumeLast() != null && consumeLast()) {
			Map<String, List<PartitionInfo>> listTopics = consumer.listTopics();
			List<TopicPartition> topicPartitions = new ArrayList<>();
			for (Entry<String, List<PartitionInfo>> entry : listTopics.entrySet()) {
				if (getTopics().equals(entry.getKey())) {
					if (entry.getValue() != null && entry.getValue().size() > 0) {
						for (PartitionInfo info : entry.getValue()) {
							topicPartitions.add(new TopicPartition(getTopics(), info.partition()));
						}
					}
					break;
				}
			}

			Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicPartitions);
			consumer.poll(0);// 第一次poll
			for (Entry<TopicPartition, Long> entry : endOffsets.entrySet()) {
				if (entry.getValue() != null) {
					consumer.seek(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	@PreDestroy
	public void preDestroy() {
		isStop = true;
		consumer.close();
		executorService.shutdown();
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
	 * @return 例：topics-user-createAccount(topics+工程名+业务)
	 */
	protected abstract String getTopics();

	/**
	 * 获取消费者 groupId
	 * 
	 * @return 例：groupId-fun(grouId+工程名)
	 */
	protected abstract String getGroupId();

	/**
	 * 处理回调消息
	 * 
	 * @param message
	 */
	protected abstract void process(List<ConsumerMessage> messages) throws Exception;

	/**
	 * 是否从最新消息开始消费,默认为 false
	 * 返回true时：从当前时间生产的数据开始消费，忽略历史生产数据
	 * 返回false时：消费所有未消费的消息
	 */
	protected abstract Boolean consumeLast();
}
