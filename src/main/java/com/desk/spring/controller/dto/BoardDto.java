package com.desk.spring.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private Long writer;
    private List<MultipartFile> files;

}
