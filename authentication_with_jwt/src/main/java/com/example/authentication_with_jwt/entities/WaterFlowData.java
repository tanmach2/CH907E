package com.example.authentication_with_jwt.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "waterFlow_data")
public class WaterFlowData {
    public WaterFlowData() {
    }

    public WaterFlowData(float dataInDay, float dataDay, float dataMonth, long deviceTime) {
        this.dataInDay = dataInDay;
        this.dataDay = dataDay;
        this.dataMonth = dataMonth;
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
    private float dataInDay; // data 6h/lan

    @Column(nullable = false)
    private float dataDay; // data 1day/lan

    @Column(nullable = false)
    private  float dataMonth; // data 1month/lan

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

    public float getDataInDay() {
        return dataInDay;
    }

    public float getDataDay() {
        return dataDay;
    }

    public float getDataMonth() {
        return dataMonth;
    }

    public long getDeviceTime() {
        return deviceTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}

