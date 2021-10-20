package com.example.authentication_with_jwt.services;

import com.example.authentication_with_jwt.entities.Device;
import com.example.authentication_with_jwt.entities.TemperatureData;
import com.example.authentication_with_jwt.repositories.DeviceRepository;
import com.example.authentication_with_jwt.repositories.TemperatureDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TemperatureDataRepository temperatureDataRepository;

    public Device getDeviceBySerial(String deviceSerial) throws Exception {
        Device device = deviceRepository.findBySeri(deviceSerial);
        if (device == null) {
            throw new Exception("Device not found");
        }
        return device;
    }


}
