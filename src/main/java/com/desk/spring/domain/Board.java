package com.desk.spring.domain;

import com.desk.spring.web.dto.BoardRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private LoginState loginState;

    private String ipAddress;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.ipAddress = boardRequestDto.getIpAddress();
        this.loginState = boardRequestDto.getLoginState();
    }

    /**
     * 연관관계 메서드
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);

        if (photo.getBoard() != this) {
            photo.setBoard(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        member.getMyBoards().add(this);
    }

    /**
     * 게시글 수정
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}