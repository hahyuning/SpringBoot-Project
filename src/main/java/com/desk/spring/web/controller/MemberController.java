package com.desk.spring.web.controller;

import com.desk.spring.domain.Member;
import com.desk.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/myPage/{id}")
    public String likeBoard(@PathVariable("id") Long memberId, Model model) {
        Optional<Member> result = memberRepository.findById(memberId);
        if (result.isPresent()) {
            Member member = result.get();
            model.addAttribute("member", member);
            model.addAttribute("boardList", member.getMyBoards());
            model.addAttribute("commentList", member.getMyComments());
        }

        return "members/myPage";
    }
}
