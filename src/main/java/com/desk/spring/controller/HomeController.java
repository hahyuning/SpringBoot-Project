package com.desk.spring.controller;

import com.desk.spring.config.oauth.dto.SessionUser;
import com.desk.spring.controller.dto.BoardResponseDto;
import com.desk.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    /*
     * 홈화면
     */
    @GetMapping("/")
    public String home(Model model) {
        List<BoardResponseDto> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }
        return "home";
    }
}
