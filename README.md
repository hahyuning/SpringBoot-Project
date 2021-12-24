# SpringBoot-project

# 사용기술
Java11, Spring Boot, OAuth2, JPA, Data JPA, MySQL

# 요구사항 분석
#### 계정
- 소셜 로그인
- 세션 관리
- 마이페이지

#### 게시판
- 게시글 CRUD
- 다중 이미지 첨부
- 익명글 작성
- 좋아요
- 댓글/대댓글

# ER 다이어그램
![image](https://user-images.githubusercontent.com/60869749/146726661-280aed13-bc49-4ea5-9ec6-5e8d3e99e167.png)

# 진행과정
- 211213 ~ 211217: 프로젝트 설계
- 211220: 프로젝트 세팅
- 211221: 소셜 로그인, 세션 기능 구현
- 211222: 게시판 CRUD, 익명글 작성 기능 구현
- 211223 ~ 211224: 댓글, 좋아요, 페이징 기능 구현
- 211225
  - 마이 페이지 구현 (data jpa, jpql)
  - 이미지 출력 (MultipartFile)

# 문제점
- 대댓글 등록/조회가 안 된다. (querydsl)
- 이미지 첨부시 화면에 동적으로 첨부가 안 된다. (ajax)
