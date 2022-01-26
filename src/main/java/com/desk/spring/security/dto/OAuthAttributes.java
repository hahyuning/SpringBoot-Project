package com.desk.spring.security.dto;

import com.desk.spring.domain.Member;
import com.desk.spring.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

// OAuth2UserService를 통해 가져온 OAuth2User의 attribute(이름, 이메일)를 담은 클래스
@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드값
    private String name;
    private String email;

    /*
     * 생성 메서드
     * registrationId를 통해 서비스에 맞춰 OAuthAttributes 객체 생성 후 반환
     */
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        // OAuth2User에서 반환하는 사용자 정보는 map이기 때문에 값 하나하나를 변환
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // naver는 사용자 정보가 response를 키로 하는 map 형태로 넘어온다.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER) // 가입 시 기본권한은 USER
                .build();
    }
}
