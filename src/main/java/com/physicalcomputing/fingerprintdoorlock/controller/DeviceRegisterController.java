package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class DeviceRegisterController {

    private final DeviceService deviceService;

    // 로그인 page show
    @GetMapping
    public String showLoginPage() {
        return "devices/login";
    }

    // 디바이스 등록 버튼을 누르면 실행
    @PostMapping("/device")
    public String register(@RequestBody ArduinoDTO.DeviceRegisterDTO deviceRegisterDTO){
        deviceService.registerDevice(deviceRegisterDTO);
        return null;
    }

}
