package com.desk.spring.controller.dto;

import com.desk.spring.domain.Board;
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

    public BoardResponseDto(Board board, List<Long> fileIdList) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.fileIdList = fileIdList;
    }

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.createdDate = board.getCreatedDate();
        this.content = board.getContent();
    }
}
