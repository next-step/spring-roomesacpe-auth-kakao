### 요구사항

##### 기능 요구사항
###### 1단계
- [x] 토큰 발급하는 API 생성(/login/token)
  - [x] DB에 존재하는 사용자에 대해 토큰 발급 (200)
  - [ ] DB에 존재하지 않는 사용자의 토큰 발급 거절
- [x] 내 정보 조회(/members/me)
  - [x] 토큰을 이용하여 본인 정보 응답 (200)

###### 2단계
- [ ] 예약하기(/reservations), 예약취소(/reservations/예약번호)
  - [ ] 예약 생성 기능 (201)
  - [ ] 예약 삭제 기능 (204)
  - [ ] 비로그인 사용자는 예약이 불가
  - [ ] 자신의 예약이 아닌 경우 예약 취소 불가

---

#### 프로그래밍 요구사항
- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
- 가능하면 Controller와 인증 로직을 분리한다.
- HandlerMethodArgumentResolver를 활용한다.