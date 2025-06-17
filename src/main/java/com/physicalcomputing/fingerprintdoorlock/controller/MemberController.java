package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.mqtt.MqttService;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import com.physicalcomputing.fingerprintdoorlock.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final DeviceService deviceService;
    private final MqttService   mqttService;

    /** 기기별 지문 등록 완료 여부 */
    private final ConcurrentHashMap<Integer, Boolean> registered = new ConcurrentHashMap<>();

    /** 기기별 임시 name/email 저장 */
    private final ConcurrentHashMap<Integer, ArduinoDTO.MemberInfoOnDeviceDTO> pending = new ConcurrentHashMap<>();

    /* ───────────────────────── 회원 등록 폼 ───────────────────────── */
    @GetMapping
    public String form(Model model) {
        model.addAttribute("member", new ArduinoDTO.MemberInfoOnDeviceDTO());
        return "members/register";
    }

    /* ───────── 브라우저: 이름 · 이메일 + 지문등록 요청 (AJAX) ───────── */
    @PostMapping("/fingerprint")
    @ResponseBody
    public ResponseEntity<Void> fingerprintRequest(
            @AuthenticationPrincipal PrincipalDetails pd,
            @RequestBody ArduinoDTO.MemberInfoOnDeviceDTO dto) {

        int deviceIdForMqtt = pd.getDevice().getDeviceIdForMqtt();

        pending.put(deviceIdForMqtt, dto);        // 이름·이메일 임시저장
        registered.put(deviceIdForMqtt, false);   // 아직 미완료

        mqttService.publish("fingerprint/" + deviceIdForMqtt, "1001"); // ESP32 지문요청
        return ResponseEntity.ok().build();
    }

    /* ─────────────────── 대기 화면 ─────────────────── */
    @GetMapping("/wait")
    public String waitPage() { return "members/wait"; }

    /* 대기 화면 폴링용 상태 API */
    @GetMapping("/fingerprint/status")
    @ResponseBody
    public boolean status(@AuthenticationPrincipal PrincipalDetails pd) {
        return registered.getOrDefault(pd.getDevice().getDeviceIdForMqtt(), false);
    }

    /* ───────── ESP32: 지문 등록 완료 알림 ───────── */
    @PostMapping("/complete")
    @ResponseBody
    public ResponseEntity<Void> complete(@RequestBody ArduinoDTO.MemberIdOnDeviceDTO idDto) {

        int deviceIdforMqtt = idDto.getDeviceIdForMqtt();      // ← 여기서 가져옴
        var info = pending.remove(deviceIdforMqtt);
        if (info == null) return ResponseEntity.badRequest().build();

        System.out.println("디바이스 번호: " + idDto.getDeviceIdForMqtt() + " 멤버번호onDevice" + idDto.getMemberIdOnDevice());

        var combined = ArduinoDTO.MemberInfoCombinedDTO.builder()
                .memberIdOnDevice(idDto.getMemberIdOnDevice())
                .name(info.getName())
                .email(info.getEmail())
                .build();

        /* 기기 엔티티는 PrincipalDetails 에서 바로 사용 */
        Device device = deviceService.getDeviceByDeviceIdForMqtt(deviceIdforMqtt);
        memberService.registerMember(device, combined);

        registered.put(deviceIdforMqtt, true);                      // 완료 표시
        return ResponseEntity.ok().build();
    }
}
