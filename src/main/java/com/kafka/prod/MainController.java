package com.kafka.prod;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainController {
	@Value("${pay.withdraw.level1.rate:0.5}")
	private BigDecimal level1Rate;
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
					kafkaImLoginProducer.produceMessage("haha"+level1Rate + count.getAndIncrement());
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
