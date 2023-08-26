package com.pool.config.serdes;

import com.pool.model.WordCount;
import org.springframework.kafka.support.serializer.JsonSerde;

public class WordCountSerDe extends JsonSerde<WordCount> {
}
