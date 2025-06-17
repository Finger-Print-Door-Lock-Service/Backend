package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final MqttService mqttService;

    @GetMapping("/dashboard")
    private String showDashboard() {
        return "devices/dashboard";
    }

    @PostMapping("/email")
    private String sendEmail(){
        return null;
    }

    @PostMapping("/door")
    @ResponseStatus(HttpStatus.NO_CONTENT)   // 204, body 없음
    public void openTheDoor(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Device d = principalDetails.getDevice();
        mqttService.publish("fingerprint/" + d.getDeviceIdForMqtt(), "1002");
    }
}
