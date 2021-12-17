package com.desk.spring.repository;

import com.desk.spring.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    // 회원가입
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 아이디로 조회
    public Member findById(String id) {
        return em.find(Member.class, id);
    }

}
