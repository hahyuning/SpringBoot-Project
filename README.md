# SpringBoot-project

# 사용기술
Java11, Spring Boot, OAuth2, JPA, Data JPA, MySQL

# 구현 기능
#### 계정
- OAuth2를 이용한 소셜 로그인과 회원 정보 관리
- 로그인한 사용자에 대한 세션 관리
- 작성한 게시글, 댓글을 확인할 수 있는 마이페이지

#### 게시판
- 게시글 등록, 조회, 수정, 삭제 기능
- 전체 게시글에 대한 페이징과 최신순 정렬 기능
- 다중 이미지 첨부와 이미지 조회, 수정 기능
- 로그인하지 않은 사용자의 익명 게시글, 익명 댓글 기능
- 댓글과 댓글에 대한 대댓글 기능

# ER 다이어그램

# 진행과정
- 211213 ~ 211217: 프로젝트 설계
- 211220: 프로젝트 세팅
- 211221: 소셜 로그인, 세션 기능 구현
- 211222: 게시판 CRUD, 익명글 작성 기능 구현
- 211223 ~ 211224: 댓글, 페이징 기능 구현
- 211225
  - 마이 페이지 (data jpa, jpql)
  - 이미지 출력 (MultipartFile)
  - 게시글 정렬 기능

# 문제점
- 대댓글 등록/조회가 안 된다. (querydsl 공부 후 적용 예정)
- 이미지 첨부시 화면에 동적으로 첨부가 안 된다. (ajax)
