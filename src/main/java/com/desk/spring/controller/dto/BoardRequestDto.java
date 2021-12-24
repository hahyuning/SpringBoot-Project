package com.desk.spring.controller.dto;

import com.desk.spring.domain.LoginState;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private Long writer;
    private LoginState loginState;
    private String ipAddress;
    private List<MultipartFile> files;

}
