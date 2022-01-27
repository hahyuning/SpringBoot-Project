package com.desk.spring.web.controller;

import com.desk.spring.security.LoginUser;
import com.desk.spring.security.dto.SessionUser;
import com.desk.spring.service.BoardService;
import com.desk.spring.web.dto.BoardRequestDto;
import com.desk.spring.web.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    /*
     * 홈화면
     */
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(required = false, defaultValue = "0", value = "page") int page,
                       @LoginUser SessionUser user) {
        Page<BoardResponseDto> boardList = boardService.findAll(page);
        boardList.stream().forEach(BoardResponseDto::getContent);
        model.addAttribute("boardList", boardList);

        if (user != null) {
            model.addAttribute("member", user);
        }
        return "home";
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 50; i++) {
            BoardRequestDto boardRequestDto = BoardRequestDto
                    .builder()
                    .title("test")
                    .content("test")
                    .build();

            try {
                boardService.create(boardRequestDto, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
