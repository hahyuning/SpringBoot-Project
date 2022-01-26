package com.desk.spring.web.file;

import lombok.Builder;
import lombok.Data;

@Data
public class PhotoDto {

    private String origFileName;
    private String filePath;
    private long fileSize;

    @Builder
    public PhotoDto(String origFileName, String filePath, long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
