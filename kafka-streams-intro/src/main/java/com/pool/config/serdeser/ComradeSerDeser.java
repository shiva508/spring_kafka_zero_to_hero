package com.pool.config.serdeser;

import com.pool.model.ComradeEvent;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ComradeSerDeser extends JsonSerde<ComradeEvent> {
}
