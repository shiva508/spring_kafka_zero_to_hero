package com.pool.basic.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfiguration {

	@Value("${studentpool.kafka.topic.name}")
	private String topicName;

	@Bean
	public IntegrationFlow integrationFlow(MessageChannel messageChannel, 
										   KafkaTemplate<Object, Object> kafkaTemplate) {
		var kafka = Kafka.outboundChannelAdapter(kafkaTemplate).topic(topicName).get();
		return IntegrationFlow.from(messageChannel).handle(kafka).get();
	}
	
	@Bean
	public MessageChannel messageChannel() {
		return MessageChannels.direct().get();
	}
}
