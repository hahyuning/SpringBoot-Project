package com.desk.spring.repository;

import com.desk.spring.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    // 게시글 등록
    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    // 게시글 조회
    public Board findById(Long boardId) {
        return em.find(Board.class, boardId);
    }

    // 게시글 전체조회
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    // 게시글 삭제
    public void delete(Board board) {
        em.remove(board);
    }
}
