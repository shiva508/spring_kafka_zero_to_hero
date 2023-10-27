package com.comrade;

import com.comrade.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringKafkaAdminUiApplication {

    @Autowired
    private KafkaConfig kafkaConfig;

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaAdminUiApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner (){
        return (args)->{
            System.out.println(kafkaConfig.getBootstrapServers());
        };
    }
}