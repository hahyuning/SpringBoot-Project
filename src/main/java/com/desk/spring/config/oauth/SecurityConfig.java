package com.desk.spring.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity // 스프링 시큐리티 설정들을 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .cors().and()
            .csrf().disable()
            // url 별 권한 관리
            .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/create").permitAll()
                .mvcMatchers(HttpMethod.GET, "/create").permitAll()
                .mvcMatchers("/").permitAll()
            //.mvcMatchers("/", "/create").permitAll()
            // .mvcMatchers("/myPage").hasRole("USER")
            // .anyRequest().authenticated()
            //.and()
            .and()
                .logout()
                    // 로그아웃 성공시 이동할 주소
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    // 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                    .userInfoEndpoint()
                        // 로그인 성공시 UserService 인터페이스의 구현체 등록
                        // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                        .userService(customOAuth2UserService);

    }
}
