package com.physicalcomputing.fingerprintdoorlock.service.memberService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Member;

public interface MemberService {

    Member getMemberByMemberIdOnDevice(int memberIdOnDevice);
    void registerMember(Device device, ArduinoDTO.MemberInfoCombinedDTO memberInfoCombinedDTO);
}
