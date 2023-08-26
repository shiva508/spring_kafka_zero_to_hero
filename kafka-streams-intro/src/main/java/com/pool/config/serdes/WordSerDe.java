package com.pool.config.serdes;

import com.pool.model.News;
import org.springframework.kafka.support.serializer.JsonSerde;

public class WordSerDe extends JsonSerde<News> {
}
