package com.johnie.kafkademo.event;

public class UpdateNodeAStatusEvent extends DomainEvent{

    public UpdateNodeAStatusEvent() {
    }

    public UpdateNodeAStatusEvent(String tenantId, String eventType) {
        super(tenantId, eventType);
    }
}
