package com.physicalcomputing.fingerprintdoorlock.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker-url}")
    private String brokeUrl;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        String clientId = MqttClient.generateClientId();
        MqttClient client = new MqttClient(brokeUrl, clientId);

        // 로그 추가
        System.out.println("🌐 Connecting to MQTT broker at " + brokeUrl);

        client.connect();

        System.out.println("mqttClient.isConnected(): " + client.isConnected());

        return client;
    }
}
