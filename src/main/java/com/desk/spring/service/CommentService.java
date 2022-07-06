package com.desk.spring.service;

import com.desk.spring.domain.Board;
import com.desk.spring.domain.Comment;
import com.desk.spring.domain.LoginState;
import com.desk.spring.domain.Member;
import com.desk.spring.repository.BoardRepository;
import com.desk.spring.repository.CommentRepository;
import com.desk.spring.repository.CommentRepositoryCustomImpl;
import com.desk.spring.repository.MemberRepository;
import com.desk.spring.web.dto.CommentRequestDto;
import com.desk.spring.web.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepositoryCustomImpl commentRepositoryCustom;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /*
     * 댓글 등록
     */
    @Transactional
    public void create(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);

        Optional<Board> result = boardRepository.findById(commentRequestDto.getBoardId());
        if (result.isPresent()) {
            Board board = result.get();
            comment.setBoard(board);
        }

        if (commentRequestDto.getLoginState() == LoginState.NAMED_USER) {
            Optional<Member> memberResult = memberRepository.findById(commentRequestDto.getMemberId());
            if (memberResult.isPresent()) {
                Member member = memberResult.get();
                comment.setMember(member);
            }
        }

        if (commentRequestDto.getParentId() != null) {
            Optional<Comment> parent = commentRepository.findById(commentRequestDto.getParentId());
            if (parent.isPresent()) {
                parent.get().setChild(comment);
                commentRepositoryCustom.updateLeftRight(comment);
                commentRepository.save(comment);
            }
        } else {
            commentRepository.save(comment);
            comment.setRootId(comment);
        }
    }

    /*
     * 댓글 삭제
     */
    @Transactional
    public void delete(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.deleteById(commentId);
        }
    }

    /*
     * 전체 댓글 조회
     */
    public List<CommentResponseDto> findAll(Long boardId) {
        return commentRepositoryCustom.findCommentByBoardId(boardId);
    }
}
