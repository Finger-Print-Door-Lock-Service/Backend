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
    public Member getMemberByMemberIdOnDevice(int memberIdOnDevice) {
        Member member = memberRepository.findByMemberIdOnDevice(memberIdOnDevice)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return member;
    }

    @Override
    public void registerMember(Device device, ArduinoDTO.MemberRegisterDTO memberRegisterDTO, ArduinoDTO.MemberInfoOnDeviceDTO memberInfoOnDeviceDTO) {

        String name = memberRegisterDTO.getName();
        String email = memberRegisterDTO.getEmail();
        int memberIdOnDevice = memberInfoOnDeviceDTO.getMemberIdOnDevice();

        Member member = Member.builder()
                .memberIdOnDevice(memberIdOnDevice) // 나중에 로그를 위해서 저장하는 것
                .name(name)
                .email(email)
                .build();

        // device and member bi-directional link
        device.addMember(member);
        member.setDevice(device);

        // 등록했을때 디바이스에 등록된 멤버의 수도 1 증가 시켜줘야함
        Device.howManyMembers++;

        memberRepository.save(member);
    }
}
