package com.pool;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

public class WikimediaClient {
	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//SAFE PRODUCER CONFIG
		properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		properties.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
		
		//HIGH THROUGHPUT
		properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
		properties.put(ProducerConfig.LINGER_MS_CONFIG, "20");
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
		String TOPIC_NAME = "wiki-topic";
		EventHandler eventHandler = new WikiMeadiaEventHandler(kafkaProducer, TOPIC_NAME);
		String url = "https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
		EventSource eventSource = builder.build();
		eventSource.start();
		TimeUnit.MINUTES.sleep(10);
	}
}