package com.pool.wiki.config;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.pool.wiki.handler.WikiEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
public class KafkaConfig {

    public static void main(String[] args) throws InterruptedException {
        String bootstrapServers="127.0.0.1:9092";
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //SAFE CONFIG
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        properties.put(ProducerConfig.RETRIES_CONFIG,Integer.toString(Integer.MAX_VALUE));
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,Integer.toString(32*1024));
        properties.put(ProducerConfig.LINGER_MS_CONFIG,"20");
        //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , "org.apache.kafka.common.serialization.LongSerializer");
        //properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        //properties.put("schema.registry.url", "http://localhost:8081");
        KafkaProducer<String, String> kafkaProducer=new KafkaProducer<>(properties);
        String topicName="batmanwiki";
        EventHandler eventHandler=new WikiEventHandler(kafkaProducer,topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder=new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource=builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(5);
    }
}
