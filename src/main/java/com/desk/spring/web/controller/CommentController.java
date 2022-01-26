package com.desk.spring.web.controller;

import com.desk.spring.domain.LoginState;
import com.desk.spring.security.LoginUser;
import com.desk.spring.security.dto.SessionUser;
import com.desk.spring.service.CommentService;
import com.desk.spring.util.ClientUtils;
import com.desk.spring.web.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*
     * 댓글 등록
     */
    @PostMapping("/comment/create")
    public String create(@ModelAttribute CommentRequestDto commentRequestDto,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request,
                         @LoginUser SessionUser user) {


        if (user != null) {
            commentRequestDto.setMemberId(user.getId());
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
