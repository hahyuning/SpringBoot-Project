package com.desk.spring.repository;

import com.desk.spring.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentByBoardId(Long boardId);
}
