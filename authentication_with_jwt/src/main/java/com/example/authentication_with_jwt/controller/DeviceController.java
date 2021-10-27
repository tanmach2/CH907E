package com.example.authentication_with_jwt.controller;


import com.example.authentication_with_jwt.entities.Device;
import com.example.authentication_with_jwt.entities.MyUser;
import com.example.authentication_with_jwt.entities.TemperatureData;
import com.example.authentication_with_jwt.entities.WaterFlowData;
import com.example.authentication_with_jwt.models.*;
import com.example.authentication_with_jwt.repositories.DeviceRepository;
import com.example.authentication_with_jwt.repositories.TemperatureDataRepository;
import com.example.authentication_with_jwt.repositories.UserRepository;
import com.example.authentication_with_jwt.repositories.WaterFlowDataRepository;
import com.example.authentication_with_jwt.services.TemperatureDataService;
import com.example.authentication_with_jwt.services.WaterFlowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/devices")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemperatureDataRepository temperatureDataRepository;

    @Autowired
    private TemperatureDataService temperatureDataService;

    @Autowired
    private WaterFlowDataService waterFlowDataService;

    @Autowired
    private WaterFlowDataRepository waterFlowDataRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<AddDeviceResponse> AddNewDevice(@RequestBody AddDeviceRequest addDeviceRequest) {
        try {
            Optional<MyUser> user = userRepository.findById(addDeviceRequest.getUserId());
            Device newDevice = new Device(addDeviceRequest.getSeri());
            newDevice.setUser(user.get());
            this.deviceRepository.save(newDevice);
            AddDeviceResponse response = new AddDeviceResponse("Add device success");
            return ResponseEntity.status(200).body(response);

        } catch (Exception err) {
            AddDeviceResponse response = new AddDeviceResponse("Add device failed");
            return ResponseEntity.status(500).body(response);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{deviceId}")
    public ResponseEntity<?> GetTemperature(@PathVariable int deviceId, @RequestParam long startTime, @RequestParam long endTime) {
        try {

//            List<TemperatureData> temperatureData = temperatureDataRepository.findByDeviceId(deviceId);
            List<TemperatureData> temperatureData = temperatureDataService.getTemperatureDataOfDeviceById(deviceId, startTime, endTime);

            ArrayList<TemperatureResponsePayload> temperatureResponsePayloads = new ArrayList<TemperatureResponsePayload>();

            temperatureData.forEach((data) -> {
                float temp = data.getTemp();
                float hum = data.getHum();
                long deviceTime = data.getDeviceTime();
                TemperatureResponsePayload temperatureResponsePayload = new TemperatureResponsePayload(temp, hum, deviceTime);
                temperatureResponsePayloads.add(temperatureResponsePayload);
            });

            TemperatureResponse temperatureResponse = new TemperatureResponse(true, temperatureResponsePayloads);
            return ResponseEntity.status(200).body(temperatureResponse);
        } catch (Exception err) {
            return ResponseEntity.status(500).body(err.getMessage());
        }
    }

    public ResponseEntity<?> GetWaterFlow(@PathVariable int deviceId){
        try{
            List<WaterFlowData> waterFlowData = waterFlowDataRepository.findByDeviceId(deviceId);
            ArrayList<WaterFlowResponsePayload> waterFlowResponsePayloads = new ArrayList<WaterFlowResponsePayload>();

            waterFlowData.forEach((data) -> {
                float dataInDay = data.getDataInDay();
                float dataDay = data.getDataDay();
                float dataMonth = data.getDataMonth();
                long deviceTime = data.getDeviceTime();
                WaterFlowResponsePayload waterFlowResponsePayload = new WaterFlowResponsePayload(dataInDay, dataDay, dataMonth,deviceTime);
                waterFlowResponsePayloads.add(waterFlowResponsePayload);
            });

            WaterFlowResponse waterFlowResponse = new WaterFlowResponse(true, waterFlowResponsePayloads);
            return ResponseEntity.status(200).body(waterFlowResponse);
        }catch (Exception err) {
            return ResponseEntity.status(500).body(err.getMessage());
        }
    }
}
