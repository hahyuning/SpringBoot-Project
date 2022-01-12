package com.desk.spring.web.dto;

import com.desk.spring.domain.comment.Comment;
import com.desk.spring.domain.member.LoginState;
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

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        if (comment.getLoginState() == LoginState.NAMED_USER) {
            this.writer = comment.getMember().getName();
        }
        else {
            this.writer = "ㅇㅇ()";
        }
        this.boardId = comment.getBoard().getId();
        this.parentId = comment.getParent().getId();
        this.createdDate = comment.getCreatedDate();
    }
}