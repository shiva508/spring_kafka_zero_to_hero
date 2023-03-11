package com.pool.basic.producer.config;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;

import com.pool.basic.producer.model.PageView;

@Configuration
public class KafkaListenerConfiguration {

	@Value("${studentpool.kafka.topic.name}")
	private String topicName;

	@KafkaListener(topics = "${studentpool.kafka.topic.name}", groupId = "${studentpool.kafka.topic.group}")
	public void onNewPageView(Message<PageView> message) {
		System.out.println("====================START=======================");
		System.out.println("Payload:" + message.getPayload());
		message.getHeaders().forEach((k, v) -> System.out.println( k + "=" + v));
		System.out.println("====================END=======================");
	}

	@Bean
	public NewTopic pageViewTopic() {
		return new NewTopic(topicName, 1, (short) 1);
	}

	@Bean
	public JsonMessageConverter jsonMessageConverter() {
		return new JsonMessageConverter();
	}

	@Bean
	public KafkaTemplate<Object, Object> kafkaTemplate(ProducerFactory<Object, Object> producerFactory) {
		return new KafkaTemplate<>(producerFactory,
				Map.of(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
	}

}
