package com.example.authentication_with_jwt.services;

import com.example.authentication_with_jwt.entities.Device;
import com.example.authentication_with_jwt.entities.TemperatureData;
import com.example.authentication_with_jwt.entities.WaterFlowData;
import com.example.authentication_with_jwt.repositories.WaterFlowDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterFlowDataService {
    @Autowired
    WaterFlowDataRepository waterFlowDataRepository;

    public void insertWaterFlowData(float dataDay, float dataInDay, float dataMonth, long deviceTime, Device device) {
        WaterFlowData newData = new WaterFlowData(dataDay, dataInDay,dataMonth, deviceTime);
        newData.setDevice(device);
        waterFlowDataRepository.save(newData);
    }
}
