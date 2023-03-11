package com.pool;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterKafkaProducer {
	Logger logger = LoggerFactory.getLogger(TwitterKafkaProducer.class);
	private static String consumerKey = "WcUBuPqdn5rz0m1ci6TE2BYrZ";
	private static String consumerSecret = "wNifA9XbINLXD4wT0zNU7fiEhcTh7MLWbqPq8QwVwGVQwPJN22";
	private static String token = "629152847-PiB5Gsk9kBzStLexlfWBvnMSAGmK4l9DRM3iGK1H";
	private static String secret = "9Cmn9gaWDQ0VfCOwnmv0gGCuWp3zfjUE6YgDilO1o8D3Y";

	public static void main(String[] args) {
		new TwitterKafkaProducer().runProcess();

	}

	private void runProcess() {
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100);
		Client twitterClient = twitterKafkaClient(msgQueue);
		twitterClient.connect();
		KafkaProducer<String, String> kafkaProducer = kafkaProducedHandler();

		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			logger.info("STOPPING APPLICATION");
			twitterClient.stop();
			kafkaProducer.close();
		}));
		while (!twitterClient.isDone()) {
			try {
				String msg = msgQueue.take();
				if (null != msg) {
					logger.info(msg);
					kafkaProducer.send(new ProducerRecord<String, String>("twitter_tweets",null, msg), new Callback() {
						
						@Override
						public void onCompletion(RecordMetadata metadata, Exception exception) {
							if(null !=exception) {
								logger.info("SOMETHING WENT WRONG");
							}
							
						}
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				twitterClient.stop();
			}

		}
logger.info("PROCESS COMPLETED");
	}

	private KafkaProducer<String, String> kafkaProducedHandler() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		/*securing connection*/
		properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		properties.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
		properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
		/* compression and batching */
		properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		properties.put(ProducerConfig.LINGER_MS_CONFIG, "20");
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
		return kafkaProducer;
	}

	private Client twitterKafkaClient(BlockingQueue<String> msgQueue) {

		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		List<String> terms = Lists.newArrayList("india","telugu");
		hosebirdEndpoint.trackTerms(terms);
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		ClientBuilder builder = new ClientBuilder().name("Hosebird-Client-01").hosts(hosebirdHosts)
				.authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));

		Client hosebirdClient = builder.build();
		return hosebirdClient;
	}
}
