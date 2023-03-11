package com.pool;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.google.gson.JsonParser;

public class KafkaConsumerClient {
	public static KafkaConsumer<String, String> kafkaConsumerClientInstance(String groupId,String bootstrapServer,String topics[]) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
		KafkaConsumer<String, String> consumer=new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(topics));
		return consumer;
	}

	public static String extractUniqueId(JsonParser jsonParser, String jsonObject,String jsonKey) {
		return jsonParser.parse(jsonObject).getAsJsonObject().get(jsonKey).getAsString();
	}

	
}
