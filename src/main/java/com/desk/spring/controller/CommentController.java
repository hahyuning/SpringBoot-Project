package com.desk.spring.controller;

import com.desk.spring.config.oauth.dto.SessionUser;
import com.desk.spring.controller.dto.CommentRequestDto;
import com.desk.spring.domain.LoginState;
import com.desk.spring.service.CommentService;
import com.desk.spring.util.ClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final HttpSession httpSession;
    private final CommentService commentService;

    /*
     * 댓글 등록
     */
    @PostMapping("/comment/create")
    public String create(@ModelAttribute CommentRequestDto commentRequestDto,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        SessionUser member = (SessionUser) httpSession.getAttribute("member");

        if (member != null) {
            commentRequestDto.setMemberId(member.getId());
            commentRequestDto.setLoginState(LoginState.NAMED_USER);
        }
        else {
            commentRequestDto.setLoginState(LoginState.ANONYMOUS);
        }

        // ip 주소 가져오기
        String ipAddress = ClientUtils.getRemoteIp(request);
        commentRequestDto.setIpAddress(ipAddress);

        commentService.create(commentRequestDto);

        redirectAttributes.addAttribute("id", commentRequestDto.getBoardId());
        return "redirect:/board/{id}/detail";
    }
}
