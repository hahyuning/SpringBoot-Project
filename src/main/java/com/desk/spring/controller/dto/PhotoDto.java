package com.desk.spring.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PhotoDto {

    private String origName;
    private String filePath;
    private Long fileSize;

    @Builder
    public PhotoDto(String origName, String filePath, Long fileSize) {
        this.origName = origName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
