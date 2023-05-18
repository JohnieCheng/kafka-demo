package com.johnie.kafkademo.event;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {
    private String eventId;
    private String tenantId;
    private Date occurredOn;
    private String eventType;

    public DomainEvent() {
    }

    public DomainEvent(String tenantId, String eventType) {
        this.tenantId = tenantId;
        this.eventType = eventType;
        this.setOccurredOn(new Date());
        this.eventId = eventType + "-" + UUID.randomUUID();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
