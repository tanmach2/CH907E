package com.example.authentication_with_jwt.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "temperature_data")
public class TemperatureData {
    public TemperatureData() {
    }

    public TemperatureData(float temp, float hum, long deviceTime) {
        this.temp = temp;
        this.hum = hum;
        this.deviceTime = deviceTime;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private float temp; // temperature: nhiệt độ

    @Column(nullable = false)
    private float hum; // humidity: độ ẩm

    @Column(name = "device_time", nullable = false)
    private long deviceTime;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public int getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public float getTemp() {
        return temp;
    }

    public float getHum() {
        return hum;
    }

    public long getDeviceTime() {
        return deviceTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
