package com.desk.spring.file.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PhotoRequestDto {

    private String origName;
    private String filePath;
    private Long fileSize;

    @Builder
    public PhotoRequestDto(String origName, String filePath, Long fileSize) {
        this.origName = origName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
