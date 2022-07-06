package com.desk.spring.web.dto;

import com.desk.spring.domain.Board;
import com.desk.spring.domain.LoginState;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private String writer;
    private LocalDateTime createdDate;
    private List<String> fileNames;
    private List<CommentResponseDto> comments;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();

        if (board.getLoginState() == LoginState.NAMED_USER) {
            this.writer = board.getMember().getName();
            this.memberId = board.getMember().getId();
        }
        else {
            this.writer = "ㅇㅇ()";
        }

        this.createdDate = board.getCreatedDate();
        this.content = board.getContent();
    }

    public void setFile(List<String> fileNames) {
        this.fileNames = fileNames;
    }
    public void setComments(List<CommentResponseDto> comments) { this.comments = comments; }
}
