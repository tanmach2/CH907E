package com.example.authentication_with_jwt.services;

import com.example.authentication_with_jwt.entities.Device;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TemperatureDataService temperatureDataService;

    @Autowired
    private IMqttClient mqttClient;

    public void publish(final String topic, final String payload, int qos, boolean retained) throws MqttPersistenceException, MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        mqttClient.publish(topic, mqttMessage);

        mqttClient.disconnect();
    }

    public void subscribe(final String topic) throws MqttException, InterruptedException {
        System.out.println("Message received: ");
        mqttClient.subscribeWithResponse(topic, (tpic, message) -> {
//            System.out.println("deviceService: " + deviceService);
//            System.out.println(message.getId()+ "->" + new String(message.getPayload()));
            if(topic == "temperature") {
                try{
                    System.out.println("received message: " + message.toString());
                    JSONObject jsonObject = new JSONObject(message.toString());
                    System.out.println(jsonObject);
                    String deviceSerial = jsonObject.getString("serial");
                    Device device = deviceService.getDeviceBySerial(deviceSerial);
                    if (device == null){
                        System.out.println("Device with serial " + deviceSerial + "  not found");

                    }else {
                        temperatureDataService.insertTemperatureData(jsonObject.getFloat("hum"),
                                jsonObject.getFloat("temp"), jsonObject.getLong("device_time"), device);
                    }
                }catch (Exception e){
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        });
    }
}
