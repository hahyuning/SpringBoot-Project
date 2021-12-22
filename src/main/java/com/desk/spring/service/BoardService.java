package com.desk.spring.service;

import com.desk.spring.controller.dto.BoardDto;
import com.desk.spring.controller.dto.BoardResponseDto;
import com.desk.spring.domain.Board;
import com.desk.spring.domain.Member;
import com.desk.spring.domain.Photo;
import com.desk.spring.repository.BoardRepository;
import com.desk.spring.repository.FileRepository;
import com.desk.spring.repository.MemberRepository;
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
    private final FileRepository fileRepository;
    private final FileHandler fileHandler;

    // 게시글 등록
    @Transactional
    public Long create(BoardDto boardDto, List<MultipartFile> files) throws Exception {
        Board board;
        if (boardDto.getWriter() != null) {
            Member member = memberRepository.findById(boardDto.getWriter()).get();
            board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .member(member)
                .build();
        }
        else {
            board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();
        }

        List<Photo> photos = fileHandler.parseFile(files);

        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                board.addFile(fileRepository.save(photo));
            }
        }
        boardRepository.save(board);
        return board.getId();
    }

    // 게시글 하나 조회
    public BoardResponseDto findById(Long boardId) {
        List<Photo> files = fileRepository.findAllByBoardId(boardId);
        List<Long> fileResults = new ArrayList<>();

        for (Photo photo : files) {
            fileResults.add(photo.getId());
        }

        Board board = boardRepository.findById(boardId);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board, fileResults);
        if (board.getMember() == null) {
            boardResponseDto.setWriter("ㅇㅇ");
        }
        else {
            boardResponseDto.setWriter(board.getMember().getName());
            boardResponseDto.setMemberId(board.getMember().getId());
        }
        return boardResponseDto;
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId);
        boardRepository.delete(board);
    }

    // 게시글 전체조회
    public List<BoardResponseDto> readAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> result = new ArrayList<>();

        for (Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            if (board.getMember() == null) {
                boardResponseDto.setWriter("ㅇㅇ");
            }
            else {
                boardResponseDto.setWriter(board.getMember().getName());
                boardResponseDto.setMemberId(board.getMember().getId());
            }
            result.add(boardResponseDto);
        }
        return result;
    }

    // 게시글 수정
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId);
        board.update(title, content);
    }
}
