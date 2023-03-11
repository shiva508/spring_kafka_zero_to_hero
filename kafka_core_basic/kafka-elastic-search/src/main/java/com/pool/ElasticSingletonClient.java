package com.pool;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;

public class ElasticSingletonClient {

	private static ElasticSingletonClient elasticSingletonClient=null;
	private static RestHighLevelClient highLevelClient=null; 
	private static ObjectMapper objectMapper =null;
	private static JsonParser jsonParser=null;
	private ElasticSingletonClient() {
		
	}

	public static ElasticSingletonClient getSingletonInstance() {
		if(elasticSingletonClient==null) {
			elasticSingletonClient=new ElasticSingletonClient();
			highLevelClient=new RestHighLevelClient(
	    	        RestClient.builder(
	    	                new HttpHost("localhost", 9200, "http")
	    	                ));
			objectMapper= new ObjectMapper();
			jsonParser=new JsonParser();
		}
		return elasticSingletonClient;
	}

	public RestHighLevelClient getHighLevelClient() {
		return highLevelClient;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public JsonParser getJsonParser() {
		return jsonParser;
	}

	
}
