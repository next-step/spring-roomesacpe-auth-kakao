## Spring Web MVC - 인증 관리

### 기능 요구사항
- [x] 토근 발급하는 API 생성
  - [x] 비밀번호 단방향 암호화 적용
- [x] 내 정보 조회하기
  - [x] 토큰을 이용하여 본인 정보 응답하기
- [x] 예약하기, 예약 취소 개선
  - [x] 비로그인 사용자는 예약이 불가능하다.
  - [x] 자신의 예약이 아닌 경우 예약 취소가 불가능하다.
- [x] 관리자 역할을 추가한다.
  - [x] 일반 멤버와 관리자 멤버를 구분한다.
- [x] 관리자 기능을 보호한다.
  - [x] 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
  - [x] 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
  - [x] 그 외 관리자 API는 자유롭게 설계하고 적용한다.

### 프로그램 요구사항
- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
  - 가능하면 Controller와 인증 로직을 분리한다.
- `HandlerMethodArgumentResolver`를 활용한다.
- 관리자를 등록하도록 하기 보다는 애플리케이션이 동작할 때 관리자는 추가될 수 있도록 한다
