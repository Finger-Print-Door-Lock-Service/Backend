package com.physicalcomputing.fingerprintdoorlock.controller;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.security.PrincipalDetails;
import com.physicalcomputing.fingerprintdoorlock.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String showRegisterMemberPage(Model model) {
        model.addAttribute("member", new ArduinoDTO.MemberRegisterDTO());
        return "members/register";
    }

    @PostMapping
    public String registerMember(@ModelAttribute ArduinoDTO.MemberRegisterDTO memberRegisterDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.registerMember(principalDetails.getDevice(), memberRegisterDTO);
        return null;
    }
}
