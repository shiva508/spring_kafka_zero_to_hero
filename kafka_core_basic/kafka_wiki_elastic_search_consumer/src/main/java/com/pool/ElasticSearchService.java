package com.pool;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElasticSearchService {

    public RestHighLevelClient elasticSearchClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http"),
                                                          new HttpHost("localhost", 9300, "http")));
    }

    public boolean isIndexExist(String indexName,RestHighLevelClient client){
        GetIndexRequest request=new GetIndexRequest(indexName) ;
        try {
            return client.indices().exists(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //this.closeConnection(client);
        }
    }

    public CreateIndexResponse createIndex(String indexName,RestHighLevelClient client){
        CreateIndexRequest createIndexRequest=new CreateIndexRequest(indexName);
        try {
          return client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //this.closeConnection(client);
        }
    }

    public void saveDateToIndex(String indexName,String key,String value,RestHighLevelClient client){

        ExecutorService executorService= Executors.newFixedThreadPool(4);
        /*executorService.execute(()->{*/
            IndexRequest indexRequest=new IndexRequest(indexName).id(key).source(value, XContentType.JSON);
            try {
                client.index(indexRequest,RequestOptions.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
               // closeConnection(client);
            }
        /*});*/

    }

    public void closeConnection(RestHighLevelClient client){
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void kafkaDataPuller(KafkaConsumer<String, String> kafkaConsumer,RestHighLevelClient client){
        while (true){
            kafkaConsumer.subscribe(Collections.singleton("batmanwiki"));
            ConsumerRecords<String,String> consumerRecords=kafkaConsumer.poll(Duration.ofMinutes(3));
            int recordCount =consumerRecords.count();
            System.out.println("recordCount========>=:"+recordCount);
            if(recordCount>0){
                consumerRecords.forEach(consumerRecord -> {

                    String key=consumerRecord.key();
                    String value=consumerRecord.value();

                    if(StringUtils.isNotBlank(key) && !key.equals("null")){
                        System.out.println(key+":"+value);
                        this.saveDateToIndex("batmanwiki",key,value,client);
                    }
                });
            }
        }
    }
}
