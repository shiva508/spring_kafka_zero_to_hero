package com.pool;

import java.util.Iterator;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerCallbackClient {
	 
	public static void main( String[] args )
    {
		final Logger logger=LoggerFactory.getLogger(KafkaProducerCallbackClient.class);
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer=new KafkaProducer<>(properties);
        for (int i=0;i<10;i++) {
        	String topic="first-topic";
        	String key="ID_"+i;
        	ProducerRecord<String, String> producerRecord=new ProducerRecord<String, String>(topic,key, "USING CALL BACK Bigining to kafka "+i);
            kafkaProducer.send(producerRecord,new Callback() {
    			@Override
    			public void onCompletion(RecordMetadata metadata, Exception exception) {
    				if(exception==null) {
    					logger.info("topic:"+metadata.topic());
    					logger.info("partition:"+metadata.partition());
    					logger.info("timestamp:"+metadata.timestamp());
    					logger.info("offset:"+metadata.offset());
    					logger.info("key:"+producerRecord.key());
    				}else {
    					logger.info("ERROR OCCURED");
    				}
    				
    			}
    		});
			
		}
        
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
