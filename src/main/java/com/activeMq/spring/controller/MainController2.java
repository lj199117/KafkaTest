package com.activeMq.spring.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainController2 {
	
	private @Autowired MessageSender messageSender;
	/*
	 * @Autowired
	 * @Qualifier("queueDestination") 
	 * private Destination destination;
	 */
	private AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/app2.xml" });
		MainController2 app = context.getBean(MainController2.class);
//		app.runQueue(); //分别注释看效果
		app.runTopic();
	}

	private void runTopic() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int num = count.incrementAndGet();
					System.out.println(num);
					// 发送更新数据请求
//					producerService_topic.sendMessage(destinationTopicName, "my name is lijin, produce:" + num);
					messageSender.sendModify("xxx"+num);
					
				}
			}
		}).start();
	}
	
	
}
