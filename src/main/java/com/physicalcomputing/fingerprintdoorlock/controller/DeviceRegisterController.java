package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoRequestDTO;
import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceRegisterController {

    private final DeviceService deviceService;
    private final MqttService mqttService;

    // 로그인 page show
    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("deviceId", new ArduinoRequestDTO.DeviceRegisterDTO());
        return "devices/login";
    }

    // 이 버튼은 db 저장용이 아니라 mqtt 연결용입니다.
    @PostMapping("/mqtt")
    public void registerDevicesForMqtt(@ModelAttribute ArduinoRequestDTO.DeviceRegisterDTO deviceRegisterDTO) {
        int deviceNumForMqtt = deviceRegisterDTO.getDeviceIdForMqtt();
        String topic = "fingerprint/" + deviceNumForMqtt;
        // 기기 등록용 요청 번호 1000
        mqttService.publish(topic, "1000");
    }

    // esp 32에서 등록 완료 후 -> spring (db 등록을 위해서)
    @PostMapping("/device")
    public void registerDevice(@RequestBody ArduinoDTO.DeviceRegisterDTO deviceRegisterDTO) {
        deviceService.registerDevice(deviceRegisterDTO);
    }
}
