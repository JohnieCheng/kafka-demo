package com.johnie.kafkademo.kafka.config;

import com.alibaba.fastjson2.JSON;
import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.event.UpdateNodeAStatusEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DomainEventSerializer implements Serializer<DomainEvent> {
    public DomainEventSerializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, DomainEvent data) {
        if (data == null) {
            return new byte[0];
        }
        String json;
        if (data instanceof UpdateNodeAStatusEvent updateNodeAStatusEvent) {
            json = JSON.toJSONString(updateNodeAStatusEvent);
        } else {
            json = JSON.toJSONString(data);
        }
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, DomainEvent data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
