package com.seojs.ptmanagerjdbc.service;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.member.MemberRepository;
import com.seojs.ptmanagerjdbc.domain.trainer.Trainer;
import com.seojs.ptmanagerjdbc.domain.trainer.TrainerRepository;
import com.seojs.ptmanagerjdbc.web.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public Member memberLogin(LoginDto loginDto) {
        return memberRepository.findByLoginId(loginDto.getLoginId())
                .filter(m -> m.getPassword().equals(loginDto.getPassword()))
                .orElse(null);
    }

    public Trainer trainerLogin(LoginDto loginDto) {
        return trainerRepository.findByLoginId(loginDto.getLoginId())
                .filter(m -> m.getPassword().equals(loginDto.getPassword()))
                .orElse(null);
    }
}
