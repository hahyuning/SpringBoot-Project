package com.desk.spring.controller;

import com.desk.spring.repository.LikeRepository;
import com.desk.spring.util.ClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeRepository likeRepository;

    @GetMapping("/like/{id}")
    public String likeBoard(@PathVariable("id") Long boardId, HttpServletRequest request) {

        String ipAddress = ClientUtils.getRemoteIp(request);
        return "";
    }
}
