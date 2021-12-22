package com.desk.spring.controller;

import com.desk.spring.config.oauth.dto.SessionUser;
import com.desk.spring.controller.dto.BoardDto;
import com.desk.spring.controller.dto.BoardResponseDto;
import com.desk.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    @GetMapping("/create")
    public String createForm() {
        return "/board/createForm";
    }

    @PostMapping("/create")
    public String createBoard(@ModelAttribute BoardDto boardDto, @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null) {
            boardDto.setWriter(member.getId());
        }
        try {
            boardService.create(boardDto, files);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        BoardResponseDto board = boardService.findById(id);
        model.addAttribute("board", board);

        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null && board.getMemberId() != null && member.getId().equals(board.getMemberId())) {
            model.addAttribute("memberId", member.getId());
        }
        return "/board/detailBoard";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardResponseDto board = boardService.findById(id);
        model.addAttribute("board", board);

        return "/board/updateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto) {
        boardService.update(boardDto.getId(), boardDto.getTitle(), boardDto.getContent());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/";
    }
}
