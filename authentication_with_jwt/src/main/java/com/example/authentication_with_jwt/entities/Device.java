package com.example.authentication_with_jwt.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String seri;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private MyUser user;

    @OneToMany(mappedBy = "device")
    private Set<TemperatureData> temperatureDataSet;

    @OneToMany(mappedBy = "device")
    private Set<WaterFlowData> waterFlowDataSet;

    public Device() {
    }

    public Device(String seri) {
        this.seri = seri;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
