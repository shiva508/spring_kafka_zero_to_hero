package com.comrade.controller;

import com.comrade.model.CreateTopicModel;
import com.comrade.service.KafkaAdminOperationsService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/topic")
public class KafkaAdminOperationsController {

    private final KafkaAdminOperationsService kafkaAdminOperationsService;

    @PostMapping("/create")
    public CreateTopicsResult createNewTopic(CreateTopicModel createTopicModel){
        return kafkaAdminOperationsService.createNewTopic(createTopicModel);
    }

    @GetMapping("/all")
    public List<String> getAllTopics(){
        return kafkaAdminOperationsService.getAllTopics();
    }
    @GetMapping("/info/{topicName}")
    public void getTopicInfo(@PathVariable("topicName") String topicName){
        kafkaAdminOperationsService.getTopicInfo(topicName);
    }
}
