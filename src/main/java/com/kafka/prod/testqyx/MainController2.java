package com.kafka.prod.testqyx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainController2 {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/app-prod.xml" });
		context.getBean(MainController2.class);
	}
}
