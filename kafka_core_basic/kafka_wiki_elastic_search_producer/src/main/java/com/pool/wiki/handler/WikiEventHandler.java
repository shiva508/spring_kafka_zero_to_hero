package com.pool.wiki.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import com.pool.avro.model.WikiModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class WikiEventHandler implements EventHandler {
    private final KafkaProducer<String, String> kafkaProducer;
    private final String topicName;
    ObjectMapper objectMapper=new ObjectMapper();

    public WikiEventHandler(KafkaProducer<String, String> kafkaProducer, String topicName) {
        this.kafkaProducer = kafkaProducer;
        this.topicName = topicName;
    }
    @Override
    public void onOpen() throws Exception {
    }
    @Override
    public void onClosed() throws Exception {
        kafkaProducer.close();
    }
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        WikiModel wikiModel=objectMapper.readValue(messageEvent.getData(),WikiModel.class);
        System.out.println(wikiModel);
        kafkaProducer.send(new ProducerRecord<>(this.topicName, String.valueOf(wikiModel.getId()), messageEvent.getData()));
    }
    @Override
    public void onComment(String s) throws Exception {
    }
    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.toString());
    }
}
