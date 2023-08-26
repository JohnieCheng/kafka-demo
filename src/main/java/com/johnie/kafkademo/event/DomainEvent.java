package com.johnie.kafkademo.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class DomainEvent implements Serializable {
    private String id;
    private String eventType;
    private String tenantId;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private Object body;
    private long contentSize;

    public DomainEvent() {
    }

    public DomainEvent(String eventType, Object body) {
        this.id = UUID.randomUUID().toString();
        this.tenantId = "johnie";
        this.eventType = eventType;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }
}
