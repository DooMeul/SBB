package com.mysite.sbb.member.controller;

import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberDto memberDto) {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto, BindingResult bindingResult) {
        log.info("====== memberDto={}", memberDto);
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        if (!memberDto.getPassword1().equals(memberDto.getPassword2())) {
            bindingResult.rejectValue("password2","passwordIncorrect","2개의 패스워드가 일치하지 않습니다.");
            return "member/signup";
        }

        memberService.createMember(memberDto);

        return  "redirect:/";
    }
}
