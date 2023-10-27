package com.comrade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTopicModel {
    private String topicName;
    private Integer noOfPartitions;
    private Short replicationFactor;
}
