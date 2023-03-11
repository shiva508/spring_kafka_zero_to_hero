package com.pool.basic.producer.config;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.pool.basic.producer.model.PageView;

@Configuration
public class ProducerRunConfiguration {
	
	@Value("${studentpool.kafka.topic.name}")
	private String topicName;

	public void kafkaSendMessage(KafkaTemplate<Object, Object> kafkaTemplate) {
		var pageView = random("kafka");
		kafkaTemplate.send(topicName, pageView);
	}

	
	public void integration(MessageChannel messageChannel) {
		var message = MessageBuilder
								    .withPayload(random("integration"))
									.copyHeaders(Map.of(KafkaHeaders.TOPIC, topicName))
									.build();
		messageChannel.send(message);
	}
	
	public void streamBridge(StreamBridge streamBridge) {
		streamBridge.send("pageViews-out-0", random("streambridge"));
	}
	
	public PageView random(String kafka) {
		var names = "shiva,satish,ravi,madhu,viju,sreenu,raju,ambi".split(",");
		var pages = "index.html,profile.html,resume.html,chat.html,career.html".split(",");
		var random = new Random();
		var name = names[random.nextInt(names.length)];
		var page = pages[random.nextInt(pages.length)];
		return new PageView(page, Math.random() > 0.5 ? 100 : 500, name, kafka);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> producerListener(KafkaTemplate<Object, Object> kafkaTemplate,
																	   MessageChannel messageChannel,
																	   StreamBridge streamBridge) {
		return event-> {
				//kafkaSendMessage(kafkaTemplate);
				//integration(messageChannel);
				this.streamBridge(streamBridge);
			
		};
	}
}
