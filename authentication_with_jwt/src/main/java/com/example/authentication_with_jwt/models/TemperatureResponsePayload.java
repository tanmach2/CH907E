package com.example.authentication_with_jwt.models;

public class TemperatureResponsePayload {
    private float temp;
    private float hum;
    private long device_time;

    public TemperatureResponsePayload() {
    }

    public TemperatureResponsePayload(float temp, float hum, long device_time) {
        this.temp = temp;
        this.hum = hum;
        this.device_time = device_time;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public long getDevice_time() {
        return device_time;
    }

    public void setDevice_time(long device_time) {
        this.device_time = device_time;
    }
}
