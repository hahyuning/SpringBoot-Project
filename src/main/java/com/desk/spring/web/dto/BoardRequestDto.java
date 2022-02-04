package com.desk.spring.web.dto;

import com.desk.spring.domain.LoginState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private Long writer;
    private LoginState loginState;
    private String ipAddress;
}
