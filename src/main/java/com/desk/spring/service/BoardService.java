package com.desk.spring.service;

import com.desk.spring.domain.Board;
import com.desk.spring.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 등록
    @Transactional
    public Long create(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId);
        boardRepository.delete(board);
    }

    // 게시글 조회

    // 게시글 수정
}
