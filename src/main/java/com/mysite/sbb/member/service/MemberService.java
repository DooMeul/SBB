package com.mysite.sbb.member.service;

import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void createMember(@Valid MemberDto memberDto) {
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword1()))
                .email(memberDto.getEmail())
                .gender(memberDto.getGender())
                .department(memberDto.getDepartment())
                .registration(memberDto.getRegistration())
                .created(LocalDateTime.now())
                .build();

        memberRepository.save(member);
    }
}
