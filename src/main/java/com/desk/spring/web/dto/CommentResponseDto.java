package com.desk.spring.web.dto;

import com.desk.spring.domain.Comment;
import com.desk.spring.domain.LoginState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private String writer;
    private Long boardId;
    private Long parentId;
    private List<CommentResponseDto> children;

    @QueryProjection
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        if (comment.getLoginState() == LoginState.NAMED_USER) {
            this.writer = comment.getMember().getName();
        }
        else {
            this.writer = "ㅇㅇ()";
        }
        this.boardId = comment.getBoard().getId();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
        this.createdDate = comment.getCreatedDate();
    }
}
