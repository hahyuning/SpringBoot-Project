package com.desk.spring.web.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.desk.spring.web.dto.QCommentResponseDto is a Querydsl Projection type for CommentResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentResponseDto extends ConstructorExpression<CommentResponseDto> {

    private static final long serialVersionUID = -1135630879L;

    public QCommentResponseDto(com.querydsl.core.types.Expression<? extends com.desk.spring.domain.Comment> comment) {
        super(CommentResponseDto.class, new Class<?>[]{com.desk.spring.domain.Comment.class}, comment);
    }

}

