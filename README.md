## 기능 요구사항
- [x] 토큰 발급하는 API 생성
  - [x] 해당 Username, PW에 해당하는 계정이 있는지 검증
- [x] 내 정보 조회하기
  - [x] 토큰을 이용하여 본인 정보 응답하기
- [x] 예약하기 개선 
  - [x] 로그인된 사용자는 예약이 가능하다 
  - [x] 비로그인 사용자는 예약이 불가능하다 -> HTTP CODE 401
- [x] 예약취소 개선
  - [x] 자신의 예약인 경우 예약 취소가 가능하다. 
  - [x] 자신의 예약이 아닌 경우 예약 취소가 불가능하다.
  - [x] 비로그인 사용자는 예약 취소가 불가능하다 -> HTTP CODE 401
- [x] 인터셉터 구현

## 프로그래밍 요구사항
- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
  - 가능하면 Controller와 인증 로직을 분리한다.
- 토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.
- HandlerMethodArgumentResolver를 활용한다.

## 3단계 구현
- [x] ErrorCode, ExceptionHandler
- [ ] 각 레이어별 test 세분화 
- [x] 관리자 역할을 추가한다.
- [x] 일반 멤버와 관리자 멤버를 구분한다.
- [x]  관리자 기능을 보호한다.
  - [x] 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
  - [x] 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
  - [x] 관리자를 등록하도록 하기보다는 애플리케이션이 동작할 때 관리자는 추가될 수 있도록 한다.


## 기능별 권한 정리
로그인 불필요 : 1 , 로그인 본인만 가능 : 2 , 관리자 계정만 가능 : 3

### Auth
- 로그인 기능(토큰 발급) : 1

### Member
- 멤버 생성 기능 : 1
- 멤버 정보 확인 기능 : 2

### Reservation
- 예약 생성 기능 : 2
- 예약 조회 기능 : 1
- 예약 삭제 기능 : 2

### Schedule
- 스케줄 생성 기능 : 3
- 스케줄 조회 기능 : 1
- 스케줄 삭제 기능 : 3

### Theme
- 테마 생성 기능 : 3
- 테마 조회 기능 : 1
- 테마 삭제 기능 : 3
