package com.desk.spring.controller.dto;

import com.desk.spring.domain.Comment;
import com.desk.spring.domain.LoginState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private String writer;
    private Long boardId;
    private Long parentId;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        if (comment.getLoginState() == LoginState.NAMED_USER) {
            this.writer = comment.getMember().getName();
        }
        else {
            this.writer = "ㅇㅇ(" + comment.getIpAddress() + ")";
        }
        this.boardId = boardId;
        this.parentId = parentId;
        this.createdDate = comment.getCreatedDate();
    }
}
