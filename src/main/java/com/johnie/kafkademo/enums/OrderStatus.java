package com.johnie.kafkademo.enums;

public enum OrderStatus {
    STATUS_ONE(0, "状态1"), STATUS_TWO(1, "状态2"), STATUS_THREE(2, "状态3");

    private int status;
    private String name;

    OrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
