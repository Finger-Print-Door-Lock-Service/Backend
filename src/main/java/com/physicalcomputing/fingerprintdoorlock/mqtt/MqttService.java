package com.physicalcomputing.fingerprintdoorlock.mqtt;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttService {

    private final MqttClient mqttClient;

    public void publish(String topic, String message) {
        try {
            System.out.println("📤 MQTT Publish: topic=" + topic + ", message=" + message);
            if (!mqttClient.isConnected()) {
                System.out.println("❌ MQTT Client is not connected");
                return;
            }
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);
            mqttClient.publish(topic, mqttMessage);
            System.out.println("✅ Message published");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
