package com.johnie.kafkademo.kafka;


import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.redis.ObjectRedisUtil;

import java.util.List;

public class KafkaUtil {
	public static final long MAX_MESSAGE_LENGTH = 1024 * 64L;

	private KafkaUtil() {
	}

	public static Object getEventBody(DomainEvent event) {
		Object obj = event.getBody();
		if (event.getContentSize() > MAX_MESSAGE_LENGTH) {
			obj = ObjectRedisUtil.get(event.getId());
		}
		return obj;
	}
}
