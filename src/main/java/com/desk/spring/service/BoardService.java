package com.desk.spring.service;

import com.desk.spring.controller.dto.BoardRequestDto;
import com.desk.spring.controller.dto.BoardResponseDto;
import com.desk.spring.domain.Board;
import com.desk.spring.domain.LoginState;
import com.desk.spring.domain.Member;
import com.desk.spring.domain.Photo;
import com.desk.spring.file.FileNameHandler;
import com.desk.spring.repository.BoardRepository;
import com.desk.spring.repository.MemberRepository;
import com.desk.spring.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final FileNameHandler fileNameHandler;

    /*
     * 게시글 등록
     */
    @Transactional
    public Long create(BoardRequestDto boardRequestDto, List<MultipartFile> files) throws Exception {
        Board board = Board.createBoard(boardRequestDto);

        // 작성자가 로그인한 회원일 경우
        if (boardRequestDto.getLoginState() == LoginState.NAMED_USER) {
            Member member = memberRepository.findById(boardRequestDto.getWriter()).get();
            board.setMember(member);
        }

        // 첨부사진 리스트
        List<Photo> photos = fileNameHandler.parseFile(files);

        // board 엔티티에 사진 등록
        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                board.addPhoto(photoRepository.save(photo));
            }
        }

        // 게시글 저장
        boardRepository.save(board);
        return board.getId();
    }

    /*
     * 게시글 한건 조회
     */
    public BoardResponseDto findById(Long boardId) {
        // 첨부사진 조회
        List<Photo> photos = photoRepository.findAllByBoardId(boardId);

        // ???
        List<Long> photoIds = new ArrayList<>();
        for (Photo photo : photos) {
            photoIds.add(photo.getId());
        }

        // 게시글 조회
        Board board = boardRepository.findById(boardId);
        return new BoardResponseDto(board, photoIds);
    }

    /*
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId);
        boardRepository.delete(board);
    }

    /*
     * 게시글 전체조회
     */
    public List<BoardResponseDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> result = new ArrayList<>();

        for (Board board : boards) {
            result.add(new BoardResponseDto(board));
        }
        return result;
    }

    /*
     * 게시글 수정
     */
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId);
        board.update(title, content);
    }
}
