package com.example.authentication_with_jwt;

import com.example.authentication_with_jwt.mqtt.MqttConfiguration;
//import com.example.authentication_with_jwt.mqtt.HandleMessage;
//import com.example.authentication_with_jwt.mqtt.MqttUtil;
//import com.example.authentication_with_jwt.mqtt.SimpleCallback;
import com.example.authentication_with_jwt.services.MessagingService;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AuthenticationWithJwtApplication implements CommandLineRunner {

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private ConfigurableApplicationContext context;


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationWithJwtApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        final String topic = "temperature";
        messagingService.subscribe(topic);
    }
}
