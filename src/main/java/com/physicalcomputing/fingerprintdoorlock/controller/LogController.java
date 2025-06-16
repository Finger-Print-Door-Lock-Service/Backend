package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.LogDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Member;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import com.physicalcomputing.fingerprintdoorlock.service.LogService.LogService;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import com.physicalcomputing.fingerprintdoorlock.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogController {

    private final DeviceService deviceService;
    private final MemberService memberService;
    private final LogService logService;

    // 로그 데이터베이스 등록 api --> firestore에 등록해야 됨
    @PostMapping
    public void saveLog(@RequestBody LogDTO logDTO) {
        logService.saveLog(logDTO);
    }

    // 로그 보여줌
    @GetMapping
    public String showLogs(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        int deviceIdForMqtt = principalDetails.getDevice().getDeviceIdForMqtt();
        List<LogDTO> logs = logService.getLogsByDeviceId(deviceIdForMqtt);
        model.addAttribute("logs", logs);
        return "logs";
    }
}
