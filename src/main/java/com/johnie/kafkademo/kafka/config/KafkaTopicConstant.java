package com.johnie.kafkademo.kafka.config;

import java.util.Arrays;
import java.util.List;

public final class KafkaTopicConstant {
    private KafkaTopicConstant() {
        throw new IllegalStateException("Utility class");
    }


    public static final String ADD_ORDER_A = "add_order_a_r1_p1";
    public static final String UPDATE_ORDER_A = "update_order_a_r1_p1";


    public static List<String> getTopicNames() {
        return Arrays.asList(ADD_ORDER_A, UPDATE_ORDER_A);
    }
}
