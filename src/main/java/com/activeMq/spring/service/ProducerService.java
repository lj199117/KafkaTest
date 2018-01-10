package com.activeMq.spring.service;

import javax.jms.Destination;

public interface ProducerService {

	void sendMessage(Destination destination, String message);

	void sendMessage(String destinationName, String message);

}
