package com.pool;

import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Properties;

public class KafkaConsumerConfig {
    private static KafkaConsumerConfig kafkaConsumerConfig;
    public Object object=new Object();
    private KafkaConsumer<String, String> kafkaConsumer;

    private RestHighLevelClient restHighLevelClient;
    private KafkaConsumerConfig(){
    }
    public static KafkaConsumerConfig getKafkaConsumerInstance(){
        synchronized (KafkaConsumerConfig.class){
            if (null==kafkaConsumerConfig){
                kafkaConsumerConfig=new KafkaConsumerConfig();
                kafkaConsumerConfig.setKafkaConsumer(createKafkaConsumer());
                kafkaConsumerConfig.setRestHighLevelClient(createElasticSearchClient());
            }
        }
        return kafkaConsumerConfig;
    }

    private static Properties getKafkaProperties(){
        String boostrapServers = "localhost:9092";
        String groupId = "batmanwikigp";
        Properties kafkaConsumerProps = new Properties();
        kafkaConsumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        kafkaConsumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        kafkaConsumerProps.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //kafkaConsumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return kafkaConsumerProps;
    }

    public static KafkaConsumer<String, String> createKafkaConsumer() {
        return new KafkaConsumer<>(getKafkaProperties());
    }

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    public static RestHighLevelClient createElasticSearchClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9300, "http")));
    }
}
