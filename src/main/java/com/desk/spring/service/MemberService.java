package com.desk.spring.service;

import com.desk.spring.domain.Member;
import com.desk.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public String join(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        if (findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        else {
            memberRepository.save(member);
            return member.getId();
        }
    }

    // 로그인
    public String login(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        if (findMember == null) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }
        if (findMember.getPassword().equals(member.getPassword())) {
            return findMember.getId();
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
