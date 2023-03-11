package com.pool;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Hello world!
 *
 */
public class BasicElasticSearchClient {
	public static void main(String[] args) throws IOException {
		ElasticSingletonClient elasticSingletonClient = ElasticSingletonClient.getSingletonInstance();
		RestHighLevelClient highLevelClient = elasticSingletonClient.getHighLevelClient();
		String tweet = elasticSingletonClient.getObjectMapper()
				.writeValueAsString(new Tweet().setName("ELON MUSK").setTweet("NEED TO GO TO MARC"));
		System.out.println(tweet);
		IndexRequest indexRequest = new IndexRequest("twitter", "tweets").source(tweet, XContentType.JSON);
		IndexResponse indexResponse = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		String id = indexResponse.getId();
		System.out.println(id);
		highLevelClient.close();
	}
}
