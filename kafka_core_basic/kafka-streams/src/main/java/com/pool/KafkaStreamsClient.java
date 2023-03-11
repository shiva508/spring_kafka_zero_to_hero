package com.pool;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */
public class KafkaStreamsClient 
{
    public static void main( String[] args )
    {
    	Properties properties=new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "demo-kafka-streams");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG , Serdes.StringSerde.class.getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        JsonParser jsonParser=new JsonParser();
        StreamsBuilder streamsBuilder=new StreamsBuilder();
        KStream<String, String> inputTopic=streamsBuilder.stream("twitter_tweets");
        KStream<String, String> filteredStreams=inputTopic.filter((key,jsonValue)->extractUniqueId(jsonParser,jsonValue)>1000);
        filteredStreams.to("important_tweets");
        KafkaStreams kafkaStreams=new KafkaStreams(streamsBuilder.build(), properties);
        kafkaStreams.start();
    }
    public static Integer extractUniqueId(JsonParser jsonParser, String jsonObject) {
    	try {
    		return jsonParser.parse(jsonObject)
    				.getAsJsonObject()
    				.get("user")
    				.getAsJsonObject()
    				.get("followers_count")
    				.getAsInt();
		} catch (Exception e) {
			return 0;
		}
	}

   
}
