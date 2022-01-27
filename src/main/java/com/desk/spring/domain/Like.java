package com.desk.spring.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}