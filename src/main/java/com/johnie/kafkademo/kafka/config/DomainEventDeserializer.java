package com.johnie.kafkademo.kafka.config;

import com.alibaba.fastjson2.JSON;
import com.johnie.kafkademo.event.DomainEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DomainEventDeserializer implements Deserializer<DomainEvent> {

    public DomainEventDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public DomainEvent deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String json = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(json, DomainEvent.class);
    }

    @Override
    public DomainEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
