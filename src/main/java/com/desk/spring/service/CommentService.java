package com.desk.spring.service;

import com.desk.spring.repository.CommentRepositoryCustomImpl;
import com.desk.spring.web.dto.CommentRequestDto;
import com.desk.spring.web.dto.CommentResponseDto;
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
        commentRepository.save(comment);
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
        List<Comment> comments = commentRepositoryCustom.findCommentByBoardId(boardId);
        return changeToDto(comments);
    }

    private List<CommentResponseDto> changeToDto(List<Comment> comments) {
        List<CommentResponseDto> result = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new HashMap<>();

        comments.forEach(c -> {
            CommentResponseDto dto = new CommentResponseDto(c);
            map.put(dto.getId(), dto);
            if (c.getParent() != null) {
                map.get(c.getParent().getId()).getChild().add(dto);
            }
            else {
                result.add(dto);
            }
        });
        return result;
    }
}
