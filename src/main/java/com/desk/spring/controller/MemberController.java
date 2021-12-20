package com.desk.spring.controller;

import com.desk.spring.config.oauth.dto.SessionUser;
import com.desk.spring.domain.Board;
import com.desk.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model) {
        List<Board> boardList = boardService.readAll();
        model.addAttribute("boardList", boardList);

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }
        return "home";
    }
}
