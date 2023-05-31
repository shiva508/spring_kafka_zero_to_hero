package com.pool;

import java.io.IOException;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class BatchElasticSearchKafkaClient {
	public static void main(String[] args) throws IOException {
		KafkaConsumer<String, String> kafkaConsumerClient = KafkaConsumerClient.kafkaConsumerClientInstance(
				ElasticSearchUtil.GROUP_ID, ElasticSearchUtil.BOOTSTRAP_SERVER, ElasticSearchUtil.TOPICS);
		ElasticSingletonClient elasticSingletonClient = ElasticSingletonClient.getSingletonInstance();
		RestHighLevelClient highLevelClient = elasticSingletonClient.getHighLevelClient();
		while (true) {
			ConsumerRecords<String, String> consumerRecords = kafkaConsumerClient.poll(Duration.ofMillis(100));
			int recordCount=consumerRecords.count();
			System.out.println("NUMBER OF RECORDS RECEIVED:"+recordCount);
			BulkRequest bulkRequest=new BulkRequest();
			for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
				System.out.println("VALUE:" + consumerRecord.value());
				String idempotent_ID = KafkaConsumerClient.extractUniqueId(elasticSingletonClient.getJsonParser(),
						consumerRecord.value(), ElasticSearchUtil.EXTRACT_JSON_VALUE);
				if(idempotent_ID !=null) {
					IndexRequest indexRequest = new IndexRequest("twitter", "tweets", idempotent_ID)
							.source(consumerRecord.value(), XContentType.JSON);
					bulkRequest.add(indexRequest);	
				}else {
					System.out.println("BAD DATA:"+consumerRecord.value());
				}
			}
			if(recordCount>0) {
				BulkResponse bulkResponse=highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
				System.out.println("COMMITTING");
				kafkaConsumerClient.commitSync();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("COMIITED DATA");
			}
		}
	}
}
