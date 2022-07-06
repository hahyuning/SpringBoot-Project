package com.desk.spring.domain;


import com.desk.spring.web.dto.CommentRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Enumerated(EnumType.STRING)
    private LoginState loginState;

    private String ipAddress;

    private Long root;

    private Long leftNum = 1L;
    private Long rightNum = 2L;

//    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        this.ipAddress = commentRequestDto.getIpAddress();
        this.loginState = commentRequestDto.getLoginState();
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getMyComments().add(this);
    }

    public void setChild(Comment child) {
        child.root = this.root;
        child.parent = this;
        child.leftNum = this.leftNum + 1;
        child.rightNum = child.leftNum + 1;

        this.children.add(child);
    }

    public void setRootId(Comment comment) {
        this.root = comment.getId();
    }
}
