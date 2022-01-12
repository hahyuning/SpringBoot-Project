package com.desk.spring.web.controller;

import com.desk.spring.config.oauth.dto.SessionUser;
import com.desk.spring.service.BoardService;
import com.desk.spring.web.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    /*
     * 홈화면
     */
    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Page<BoardResponseDto> boardList = boardService.findAll(page);
        boardList.stream().forEach(b -> b.getContent());
        model.addAttribute("boardList", boardList);

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null) {
            model.addAttribute("member", member);
        }
        return "home";
    }

//    @PostConstruct
//    public void init() {
//        for (int i = 0; i < 50; i++) {
//            BoardRequestDto boardRequestDto = new BoardRequestDto();
//            boardRequestDto.setTitle("test");
//            boardRequestDto.setContent("test");
//            boardRequestDto.setIpAddress("127.0.0.0");
//
//            try {
//                boardService.create(boardRequestDto);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
