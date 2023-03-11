package com.pool;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

public class WikiMeadiaEventHandler implements EventHandler {

	private KafkaProducer<String, String> kafkaProducer;
	private String topic;

	public WikiMeadiaEventHandler(KafkaProducer<String, String> kafkaProducer, String topic) {
		this.kafkaProducer = kafkaProducer;
		this.topic = topic;
	}

	@Override
	public void onOpen() throws Exception {

	}

	@Override
	public void onClosed() throws Exception {
		kafkaProducer.close();
	}

	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		System.out.println(messageEvent.getData());
		kafkaProducer.send(new ProducerRecord<String, String>(this.topic, messageEvent.getData()));
	}

	@Override
	public void onComment(String comment) throws Exception {

	}

	@Override
	public void onError(Throwable t) {

	}

}
