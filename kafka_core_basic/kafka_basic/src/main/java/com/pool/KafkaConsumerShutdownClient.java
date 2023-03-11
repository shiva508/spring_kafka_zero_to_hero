package com.pool;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerShutdownClient {
	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(KafkaConsumerShutdownClient.class);
		Properties properties = new Properties();
		String groupid="batman";
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupid);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		KafkaConsumer<String, String> consumer=new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList("important_tweets","first-topic","second_topic"));
		while (true) {
			ConsumerRecords<String, String> consumerRecords=consumer.poll(Duration.ofMillis(100));
			consumerRecords.forEach(data->{
				logger.info("KEY:"+data.key()+"    VALUE:"+data.value());
				logger.info("partition:"+data.partition()+"    offset:"+data.offset());
			});
		}
	}
}
