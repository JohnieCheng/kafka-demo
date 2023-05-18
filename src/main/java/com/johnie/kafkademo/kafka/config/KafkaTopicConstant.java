package com.johnie.kafkademo.kafka.config;

public final class KafkaTopicConstant {
    private KafkaTopicConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String UPDATE_NODE_A_STATUS = "update_node_a_status_r1_p1";
    public static final String UPDATE_NODE_B_STATUS = "update_node_b_status_r1_p1";
    public static final String UPDATE_NODE_C_STATUS = "update_node_c_status_r1_p1";
}
