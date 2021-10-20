package com.example.authentication_with_jwt.services;

import com.example.authentication_with_jwt.entities.Device;
import com.example.authentication_with_jwt.entities.TemperatureData;
import com.example.authentication_with_jwt.repositories.TemperatureDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureDataService {
    @Autowired
    TemperatureDataRepository temperatureDataRepository;

    public void insertTemperatureData(float temp, float hum, long deviceTime, Device device) {
        TemperatureData newData = new TemperatureData(temp, hum, deviceTime);
        newData.setDevice(device);
        temperatureDataRepository.save(newData);
    }

    public List<TemperatureData> getTemperatureDataOfDeviceById(int deviceId, long startTime, long endTime) {
        List<TemperatureData> temperatureDataList = temperatureDataRepository.findByDeviceIdAndDeviceTimeGreaterThanAndDeviceTimeLessThan(deviceId, startTime, endTime);
        return temperatureDataList;
    }
}
