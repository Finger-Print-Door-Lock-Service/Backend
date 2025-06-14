package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mqtt")
public class MqttTestController {

    private final MqttService mqttService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestParam String message) {
        mqttService.publish(topic, message);
        return ResponseEntity.ok("메시지 전송 완료");
    }
}
