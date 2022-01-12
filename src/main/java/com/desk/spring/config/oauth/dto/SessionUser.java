package com.desk.spring.config.oauth.dto;

import com.desk.spring.domain.member.Member;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.io.Serializable;

@Getter
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;

    public SessionUser() {
    }

    public SessionUser(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
