package com.desk.spring.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoDto {

    private Long id;
    private Long boardId;
    private String origFileName;
    private String saveName;
    private long fileSize;
    private String uploadPath;
}
