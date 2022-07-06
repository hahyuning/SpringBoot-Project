package com.desk.spring.repository;

import com.desk.spring.domain.Comment;
import com.desk.spring.web.dto.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> findCommentByBoardId(Long boardId);
    void updateLeftRight(Comment comment);
}
