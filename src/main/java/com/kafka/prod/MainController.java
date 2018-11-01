package com.kafka.prod;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainController {
	
	@Autowired private KafkaImLoginProducer kafkaImLoginProducer;

	private AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/app-prod.xml" });
		MainController app = context.getBean(MainController.class);
		app.produce();
	}

	private void produce() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					kafkaImLoginProducer.produceMessage("haha" + count.getAndIncrement());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
