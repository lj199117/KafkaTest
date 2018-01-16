package com.activeMq.spring.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.activeMq.spring.service.ProducerService;

@Component
public class MainController {
	
	@Autowired
	@Qualifier("queue")
	private ProducerService producerService_queue;
	@Autowired
	@Qualifier("topic")
	private ProducerService producerService_topic;

	private static final String destinationQueueName = "test.queue";
	private static final String destinationTopicName = "test.topic";
	/*
	 * @Autowired
	 * @Qualifier("queueDestination") 
	 * private Destination destination;
	 */
	private AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/app.xml" });
		MainController app = context.getBean(MainController.class);
//		app.runQueue(); //分别注释看效果
		app.runTopic();
	}

	private void runQueue() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					int num = count.incrementAndGet();
					// 发送更新数据请求
					producerService_queue.sendMessage(destinationQueueName, "my name is lijin, produce:" + num);
					producerService_queue.sendMessage("test.queue.log", "my name is lijin-log, produce:" + num);
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	
	
	private void runTopic() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					int num = count.incrementAndGet();
					// 发送更新数据请求
					producerService_topic.sendMessage(destinationTopicName, "my name is lijin, produce:" + num);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
}
