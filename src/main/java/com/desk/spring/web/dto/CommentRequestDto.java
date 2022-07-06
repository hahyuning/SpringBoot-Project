package com.desk.spring.web.dto;

import com.desk.spring.domain.LoginState;
import lombok.Builder;
import lombok.Data;

@Data
public class CommentRequestDto {

    private Long id;
    private String content;
    private Long memberId;
    private Long boardId;
    private LoginState loginState;
    private String ipAddress;
    private Long parentId;

    @Builder
    public CommentRequestDto(String content, Long memberId, Long boardId) {
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
