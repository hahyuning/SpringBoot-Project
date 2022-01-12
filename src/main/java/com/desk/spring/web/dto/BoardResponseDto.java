package com.desk.spring.web.dto;

import com.desk.spring.domain.board.Board;
import com.desk.spring.domain.member.LoginState;
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
    private List<Long> fileIdList;

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

    public void setFile(List<Long> fileIdList) {
        this.fileIdList = fileIdList;
    }
}
