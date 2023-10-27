package com.comrade.service;

import com.comrade.model.CreateTopicModel;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class KafkaAdminOperationsService {

    private AdminClient adminClient;

    public List<String> getAllTopics(){
        List<String> topics = null;
        try {
            topics = adminClient.listTopics().namesToListings().get().entrySet().stream().map(Map.Entry::getKey).toList();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return topics;
    }

    public CreateTopicsResult createNewTopic(CreateTopicModel createTopicModel){
        var topicName = createTopicModel.getTopicName();
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG,TopicConfig.CLEANUP_POLICY_COMPACT);
        topicConfig.put(TopicConfig.COMPRESSION_TYPE_CONFIG, "lz4");
        NewTopic newTopic = new NewTopic(topicName, createTopicModel.getNoOfPartitions(), createTopicModel.getReplicationFactor()).configs(topicConfig);
        CreateTopicsResult createTopicsResult = adminClient.createTopics(List.of(newTopic));
        KafkaFuture<Void>  future = createTopicsResult.values().get(topicName);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return createTopicsResult;
    }

    public void getTopicInfo(String topicName){
        adminClient.describeTopics(List.of(topicName)).topicNameValues().forEach((s, topicDescriptionKafkaFuture) -> {
            System.out.println(s +"<===>"+topicDescriptionKafkaFuture);
        });
        try {
            adminClient.describeTopics(List.of(topicName)).all().get().forEach((s, topicDescription) -> {
                System.out.println(s +"<===>"+topicDescription);
            });
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
