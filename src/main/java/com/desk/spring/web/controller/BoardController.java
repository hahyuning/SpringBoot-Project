package com.desk.spring.web.controller;

import com.desk.spring.domain.LoginState;
import com.desk.spring.security.LoginUser;
import com.desk.spring.security.dto.SessionUser;
import com.desk.spring.service.BoardService;
import com.desk.spring.service.CommentService;
import com.desk.spring.util.ClientUtils;
import com.desk.spring.web.dto.BoardRequestDto;
import com.desk.spring.web.dto.BoardResponseDto;
import com.desk.spring.web.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    /*
     * 게시글 작성 폼
     */
    @GetMapping("/board/create")
    public String createForm() {
        return "/board/createForm";
    }

    /*
     * 게시글 등록
     */
    @PostMapping("/board/create")
    public String createBoard(@ModelAttribute BoardRequestDto boardRequestDto,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request,
                              @LoginUser SessionUser user) throws Exception {

        // 로그인한 사용자일 경우 writer 등록
        if (user != null) {
            boardRequestDto.setWriter(user.getId());
            boardRequestDto.setLoginState(LoginState.NAMED_USER);
        }
        else {
            boardRequestDto.setLoginState(LoginState.ANONYMOUS);
        }

        // ip 주소 가져오기
        String ipAddress = ClientUtils.getRemoteIp(request);
        boardRequestDto.setIpAddress(ipAddress);

        try {
            Long boardId = boardService.create(boardRequestDto);
            redirectAttributes.addAttribute("id", boardId);
            return "redirect:/board/{id}/detail";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/board/create";
        }
    }

    /*
     * 게시글 한건 조회
     */
    @GetMapping("board/{id}/detail")
    public String detail(@PathVariable("id") Long id,
                         Model model,
                         @LoginUser SessionUser user) {

        BoardResponseDto boardResponseDto = boardService.findById(id);
        model.addAttribute("board", boardResponseDto);

        List<CommentResponseDto> commentList = commentService.findAll(id);
        model.addAttribute("commentList", commentList);

        // 조회한 사람과 작성한 사람 비교
        if (user != null && boardResponseDto.getMemberId() != null && user.getId().equals(boardResponseDto.getMemberId())) {
            model.addAttribute("memberId", user.getId());
        }
        return "/board/detailBoard";
    }

    /*
     * 게시글 수정 폼
     */
    @GetMapping("/board/{id}/update")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        BoardResponseDto board = boardService.findById(id);
        model.addAttribute("board", board);

        return "/board/updateForm";
    }

    /*
     * 게시글 수정
     */
    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardRequestDto boardRequestDto) {
        boardService.update(boardRequestDto.getId(), boardRequestDto.getTitle(), boardRequestDto.getContent());
        return "redirect:/";
    }

    /*
     * 게시글 삭제
     */
    @GetMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/";
    }
}
