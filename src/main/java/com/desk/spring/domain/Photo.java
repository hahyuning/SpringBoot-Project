package com.desk.spring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String origName;

    @Column(nullable = false)
    private String filePath;

    private Long fileSize;

    @ManyToOne()
    @JoinColumn(name = "board_id")
    private Board board;

    public Photo(String origName, String filePath, Long fileSize) {
        this.origName = origName;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }

    public void setBoard(Board board) {
        this.board = board;

        if (!board.getFiles().contains(this)) {
            board.getFiles().add(this);
        }
    }
}
