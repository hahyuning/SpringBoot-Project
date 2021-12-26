package com.desk.spring.service;

import com.desk.spring.controller.dto.CommentRequestDto;
import com.desk.spring.controller.dto.CommentResponseDto;
import com.desk.spring.domain.Board;
import com.desk.spring.domain.Comment;
import com.desk.spring.domain.LoginState;
import com.desk.spring.domain.Member;
import com.desk.spring.repository.BoardRepository;
import com.desk.spring.repository.CommentRepository;
import com.desk.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /*
     * 댓글 등록
     */
    @Transactional
    public void create(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);

        Board board = boardRepository.findById(commentRequestDto.getBoardId()).get();
        comment.setBoard(board);

        if (commentRequestDto.getLoginState() == LoginState.NAMED_USER) {
            Member member = memberRepository.findById(commentRequestDto.getMemberId()).get();
            comment.setMember(member);
        }

        commentRepository.save(comment);
    }

    /*
     * 댓글 삭제
     */
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        commentRepository.delete(comment);
    }

    /*
     * 전체 댓글 조회
     */
    public List<CommentResponseDto> findAll(Long boardId) {
        System.out.println(boardId);
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        List<CommentResponseDto> result = new ArrayList<>();

        System.out.println("조회는 한듯");
        for (Comment comment : comments) {
            result.add(new CommentResponseDto(comment));
        }
        return result;
    }
}