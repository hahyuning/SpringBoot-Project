package com.desk.spring.repository;

import com.desk.spring.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    /*
     * 댓글 등록
     */
    public void save(Comment comment) {
        em.persist(comment);
    }

    /*
     * 댓글 삭제
     */
    public void delete(Comment comment) {
        em.remove(comment);
    }

    /*
     * boardId로 댓글 조회
     */
    public List<Comment> findByBoardId(Long boardId) {
        return em.createQuery("select c from Comment c inner join c.board b where b.id = :boardId", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    /*
     * id로 댓글 조회
     */
    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }
}
