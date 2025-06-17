package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import com.physicalcomputing.fingerprintdoorlock.service.mailService.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final MqttService mqttService;
    private final MailService mailService;
    private final DeviceService deviceService;

    @GetMapping("/dashboard")
    private String showDashboard() {
        return "devices/dashboard";
    }

    @PostMapping("/alert/{deviceIdForMqtt}")
    public ResponseEntity<Void> sendEmail(@PathVariable int deviceIdForMqtt) {
        String to = deviceService.getDeviceByDeviceIdForMqtt(deviceIdForMqtt).getEmail();
        mailService.sendAlertEmail(to, "연속 3번 에러", "연속 3번 에러");
        System.out.println("전송완료");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/door")
    @ResponseStatus(HttpStatus.NO_CONTENT)   // 204, body 없음
    public void openTheDoor(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Device d = principalDetails.getDevice();
        mqttService.publish("fingerprint/" + d.getDeviceIdForMqtt(), "1002");
    }
}
