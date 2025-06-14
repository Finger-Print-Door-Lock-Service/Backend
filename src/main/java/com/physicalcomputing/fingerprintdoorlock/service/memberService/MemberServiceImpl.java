package com.physicalcomputing.fingerprintdoorlock.service.memberService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Member;
import com.physicalcomputing.fingerprintdoorlock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void registerMember(Device device, ArduinoDTO.MemberRegisterDTO memberRegisterDTO) {

        String name = memberRegisterDTO.getName();
        String email = memberRegisterDTO.getEmail();

        Member member = Member.builder()
                .name(name)
                .email(email)
                .build();

        // device and member bi-directional link
        device.addMember(member);
        member.setDevice(device);

        memberRepository.save(member);
    }
}
