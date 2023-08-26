package com.pool.config.producer;

import com.pool.model.ComradeEvent;
import com.pool.model.JoinedValue;
import com.pool.util.Deportment;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class ComradeKafkaProducerConfig {

    @Bean
    public Supplier<Message<ComradeEvent>> messageProducer(){
        List<String> names = Arrays.asList("Shiva", "Satish", "Gopi", "Ravi", "Rajeshwari", "Mounika", "Kavya");
        return ()-> {
            Deportment deportment = Deportment.values()[new Random().nextInt(Deportment.values().length)];
            ComradeEvent comradeEvent = new ComradeEvent(names.get(new Random().nextInt(names.size() - 1)), deportment);
            return MessageBuilder.withPayload(comradeEvent)
                    .setHeader(KafkaHeaders.KEY, deportment.name())
                    .build();
        };
    }

    @Bean
    public Function<KStream<String,ComradeEvent>, KStream<String,String>> messageEnhancer(){
        return inputEvent->inputEvent.mapValues(ComradeEvent::name);
    }

    @Bean
    public Function<KStream<String,ComradeEvent>,KStream<String,String>> messageAggregate(){
        return inputEnhanced->inputEnhanced
                //.peek((key, value) -> System.out.println("Key="+key+",Value"+value))
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(8)))
                .aggregate(() -> 0l,(key, value, aggregate)->aggregate+1, Materialized.with(Serdes.String(),Serdes.Long()))
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((stringWindowed, aLong) -> new KeyValue<>(stringWindowed.key() , aLong.toString()));
    }

    @Bean
    public BiFunction<KStream<String,String>,KStream<String,String>,KStream<String, JoinedValue>> messageJoin(){
        return (inputValueOne,inputValueTwo)->inputValueOne.join(inputValueTwo, JoinedValue::new,
                                                                 JoinWindows.ofTimeDifferenceWithNoGrace(Duration.of(10, ChronoUnit.SECONDS)),
                                                                 StreamJoined.with(Serdes.String(),Serdes.String(),Serdes.String()));
    }
}
