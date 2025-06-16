package com.physicalcomputing.fingerprintdoorlock.service.memberService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;

public interface MemberService {

    void registerMember(Device device, ArduinoDTO.MemberRegisterDTO memberRegisterDTO, ArduinoDTO.MemberInfoOnDeviceDTO memberInfoOnDeviceDTO);
}
