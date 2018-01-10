package com.activeMq.spring.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.activeMq.spring.service.ProducerService;

@Component
public class MainController {
	@Autowired
	private ProducerService producerService;

	@Resource
	private JmsTemplate jmsQueueTemplate;

	private static final String destinationName = "test.queue";
	/*
	 * @Autowired
	 * @Qualifier("queueDestination") 
	 * private Destination destination;
	 */
	private AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/app.xml" });
		MainController app = context.getBean(MainController.class);
		app.run();
	}

	private void run() {
		jmsQueueTemplate.getConnectionFactory();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					int num = count.incrementAndGet();
					// 发送更新数据请求
					producerService.sendMessage(destinationName, "my name is lijin, produce:" + num);
					
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
