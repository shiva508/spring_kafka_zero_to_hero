package com.comrade;

import com.comrade.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaAdminUiApplication {

    @Autowired
    private KafkaConfig kafkaConfig;

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaAdminUiApplication.class, args);
    }

}