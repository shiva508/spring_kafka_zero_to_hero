package com.pool.config.producer;

import com.pool.model.ComradeEvent;
import com.pool.util.Deportment;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
}
