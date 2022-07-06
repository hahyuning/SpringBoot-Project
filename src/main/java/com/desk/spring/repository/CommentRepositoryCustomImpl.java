package com.desk.spring.repository;

import com.desk.spring.domain.Comment;
import com.desk.spring.domain.QComment;
import com.desk.spring.web.dto.CommentResponseDto;
import com.desk.spring.web.dto.QCommentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QComment comment = QComment.comment;

    @Override
    public List<CommentResponseDto> findCommentByBoardId(Long boardId) {

        return queryFactory.select(new QCommentResponseDto(comment))
                .from(comment)
                .where(comment.board.id.eq(boardId))
                .orderBy(comment.root.asc(), comment.leftNum.asc())
                .fetch();
    }

    public void updateLeftRight(Comment newComment) {
        queryFactory.update(comment)
                .set(comment.leftNum, comment.leftNum.add(2))
                .where(comment.root.eq(newComment.getRoot()).and(comment.leftNum.goe(newComment.getRightNum())))
                .execute();

        queryFactory.update(comment)
                .set(comment.rightNum, comment.rightNum.add(2))
                .where(comment.root.eq(newComment.getRoot()).and(comment.rightNum.goe(newComment.getLeftNum())))
                .execute();
    }
}
