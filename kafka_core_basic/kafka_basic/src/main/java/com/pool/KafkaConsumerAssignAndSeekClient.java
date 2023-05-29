package com.pool;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerAssignAndSeekClient {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(KafkaConsumerAssignAndSeekClient.class);
		Properties properties = new Properties();
		String topic = "batman";
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
		TopicPartition topicPartitionReadFrom = new TopicPartition(topic, 3);
		consumer.assign(Arrays.asList(topicPartitionReadFrom));
		Long offsetToReadFrom = 15L;
		int numberOfMessagesReadSoFor = 0;
		int numberOfMessagesToRead = 5;
		boolean keepReading = true;
		consumer.seek(topicPartitionReadFrom, offsetToReadFrom);
		while (keepReading) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> data : consumerRecords) {
				numberOfMessagesReadSoFor += 1;
				logger.info("KEY:" + data.key() + "    VALUE:" + data.value());
				if (numberOfMessagesReadSoFor >= numberOfMessagesToRead) {
					keepReading = false;
					break;
				}
			}
		}
	}
}
