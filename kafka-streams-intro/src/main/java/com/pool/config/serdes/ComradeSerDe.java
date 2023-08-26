package com.pool.config.serdes;

import com.pool.model.ComradeEvent;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ComradeSerDe extends JsonSerde<ComradeEvent> {
}
