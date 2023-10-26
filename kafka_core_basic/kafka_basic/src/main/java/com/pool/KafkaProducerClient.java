package com.pool;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
public class KafkaProducerClient 
{
    public static void main( String[] args )
    {
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.21.0.3:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer=new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord=new ProducerRecord<String, String>("batman", "Welcome to Bat Cave Robbin");
        kafkaProducer.send(producerRecord);
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
