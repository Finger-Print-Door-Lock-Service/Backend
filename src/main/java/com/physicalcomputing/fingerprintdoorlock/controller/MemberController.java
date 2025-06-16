package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import com.physicalcomputing.fingerprintdoorlock.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MqttService mqttService;

    // 이페이지를 누르면 지금
    @GetMapping
    public String showRegisterMemberPage(Model model) {
        model.addAttribute("member", new ArduinoDTO.MemberRegisterDTO());
        return "members/register";
    }

    // 기기 등록 요청 번호 : 1001
    @PostMapping("/fingerprint")
    public ResponseEntity<?> requestFingerprint(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        long deviceId = principalDetails.getDevice().getDeviceId(); // 현재 로그인한 기기의 ID
        String topic = "/fingerprint/" + deviceId + "/request";
        String payload = "1001";
        mqttService.publish(topic, payload);
        return ResponseEntity.ok().build();
    }

    // 이건 esp 32가 서버에게 정보를 주는 것 -> 1. 지문 deivced에서의 memberId
    @PostMapping
    public String registerMember(@ModelAttribute ArduinoDTO.MemberRegisterDTO memberRegisterDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @RequestBody ArduinoDTO.MemberInfoOnDeviceDTO memberInfoOnDeviceDTO) {
        memberService.registerMember(principalDetails.getDevice(), memberRegisterDTO, memberInfoOnDeviceDTO);
        return "redirect:/dashboard";
    }
}
