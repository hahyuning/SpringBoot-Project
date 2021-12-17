package com.desk.spring.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String origName;
    private String fileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "files")
    private Board board;
}
