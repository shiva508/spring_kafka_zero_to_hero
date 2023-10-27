package com.comrade.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaConfig {
    private List<String> bootstrapServers;
}
