package com.kafka.basic2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.40.203:9097,192.168.40.203:9098");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++){
        	producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
        	System.out.printf("key = %s, value = %s\n", Integer.toString(i), Integer.toString(i));
        	try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }

        producer.close();
    }
}
