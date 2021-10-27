package com.example.authentication_with_jwt.models;

public class WaterFlowResponsePayload {
    private float dataInDay;
    private float dataDay;
    private float dataMonth;
    private long device_time;

    public WaterFlowResponsePayload() {
    }

    public WaterFlowResponsePayload(float dataInDay, float dataDay, float dataMonth, long device_time) {
        this.dataInDay = dataInDay;
        this.dataDay = dataDay;
        this.dataMonth = dataMonth;
        this.device_time = device_time;
    }

    public float getDataInDay() {
        return dataInDay;
    }

    public void setDataInDay(float dataInDay) {
        this.dataInDay = dataInDay;
    }

    public float getDataDay() {
        return dataDay;
    }

    public void setDataDay(float dataDay) {
        this.dataDay = dataDay;
    }

    public float getDataMonth() {
        return dataMonth;
    }

    public long getDevice_time() {
        return device_time;
    }

    public void setDevice_time(long device_time) {
        this.device_time = device_time;
    }

    public void setDataMonth(float dataMonth) {
        this.dataMonth = dataMonth;
    }
}
