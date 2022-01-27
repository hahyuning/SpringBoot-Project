package com.desk.spring.service;

import com.desk.spring.domain.Board;
import com.desk.spring.domain.LoginState;
import com.desk.spring.domain.Member;
import com.desk.spring.domain.Photo;
import com.desk.spring.web.dto.PhotoDto;
import com.desk.spring.repository.BoardRepository;
import com.desk.spring.repository.MemberRepository;
import com.desk.spring.repository.PhotoRepository;
import com.desk.spring.util.FileUtils;
import com.desk.spring.web.dto.BoardRequestDto;
import com.desk.spring.web.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final FileUtils fileUtils;

    /*
     * 게시글 등록
     */
    @Transactional
    public Long create(BoardRequestDto boardDto, MultipartFile[] files) {
        Board board = new Board(boardDto);

        // 작성자가 로그인한 회원일 경우
        if (boardDto.getLoginState() == LoginState.NAMED_USER) {
            Optional<Member> result = memberRepository.findById(boardDto.getWriter());
            result.ifPresent(board::setMember);
        }

        // 첨부사진 리스트
        List<PhotoDto> photoDtos = fileUtils.uploadPhotos(files, board.getId());
        List<Photo> photos = photoDtos.stream()
                .map(Photo::new).collect(Collectors.toList());

        // board 엔티티에 사진 등록
        if (!CollectionUtils.isEmpty(photos)) {
            for (Photo photo : photos) {
                photoRepository.save(photo);
                board.addPhoto(photo);
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
        Optional<Board> result = boardRepository.findById(boardId);
        if (result.isPresent()) {
            Board board = result.get();
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            boardResponseDto.setFile(photoIds);
            return boardResponseDto;
        }

        return null;
    }

    /*
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    /*
     * 게시글 전체조회
     */
    public Page<BoardResponseDto> findAll(int page) {

        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate")));
        return boards.map(BoardResponseDto::new);
    }

    /*
     * 게시글 수정
     */
    @Transactional
    public void update(Long boardId, String title, String content) {

        Optional<Board> result = boardRepository.findById(boardId);
        if (result.isPresent()) {
            Board board = result.get();
            board.update(title, content);
        }
    }
}
