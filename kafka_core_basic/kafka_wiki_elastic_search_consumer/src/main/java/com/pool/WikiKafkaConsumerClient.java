package com.pool;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;

public class WikiKafkaConsumerClient {
    public static void main(String[] args) {

        ElasticSearchService elasticSearchService=new ElasticSearchService();
        KafkaConsumerConfig factoryObject= KafkaConsumerConfig.getKafkaConsumerInstance();
        RestHighLevelClient restHighLevelClient= factoryObject.getRestHighLevelClient();
        boolean exist=elasticSearchService.isIndexExist("batmanwiki",restHighLevelClient);
        if(!exist){
            CreateIndexResponse createIndexResponse =elasticSearchService.createIndex("batmanwiki",restHighLevelClient);
        }

        KafkaConsumer<String,String> kafkaConsumer= factoryObject.getKafkaConsumer();
        elasticSearchService.kafkaDataPuller(kafkaConsumer,restHighLevelClient);
        elasticSearchService.closeConnection(restHighLevelClient);
    }
}