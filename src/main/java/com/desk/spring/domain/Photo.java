package com.desk.spring.domain;

import com.desk.spring.web.dto.PhotoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String origName;

    @Column(nullable = false)
    private String saveName;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String uploadPath;

    public Photo(PhotoDto photoDto) {
        this.origName = photoDto.getOrigFileName();
        this.saveName = photoDto.getSaveName();
        this.fileSize = photoDto.getFileSize();
        this.uploadPath = photoDto.getUploadPath();
    }

    /*
     * 연관관계 메서드
     */
    public void setBoard(Board board) {
        this.board = board;

        if (!board.getPhotos().contains(this)) {
            board.getPhotos().add(this);
        }
    }
}
