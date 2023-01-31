# 방탈출 예약 인증 서비스

### 기능 요구사항

* 토큰 발급하는 API 생성
* 내 정보 조회하기
  * 토큰을 이용하여 본인 정보 응답하기
* 예약하기, 예약취소 개선
  * 아래의 API 설계에 맞춰 API 스펙을 변경한다.
  * 비로그인 사용자는 예약이 불가능하다.
  * 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

**프로그래밍 요구사항**

* 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
  * 가능하면 Controller와 인증 로직을 분리한다.
* 토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.
* `HandlerMethodArgumentResolver`를 활용한다.
ik9

### 기능 설명
* `data.sql`에 Theme, Member, Schedule에 대한 쿼리가 포함되어 있음

**토큰 발급** 을 이용하여 유저의 토큰 생성
  ![TokenGenerator.png](TokenGenerator.png)

**내 정보 조회** - 유저 정보로 발급받은 토큰을 이용하여 조회
![LookUpInfo.png](LookUpInfo.png)

**예약 생성** - 발급받은 토큰과 생성되어 있는 ScheduleId를 이용하여 예약 생성
![reservationCreate.png](reservationCreate.png)

**예약 삭제** - 생성되어 있는 예약을 삭제
![reservationDelete.png](reservationDelete.png)

### TestCase
**Token**
* 토큰을 생성할 수 있다
* 토큰을 이용하여 유저 정보를 가져올 수 있다.

**Member**
* 멤버를 생성할 수 있다
* 조회를 할 때 로그인이 되지 않았을 경우, 에러 발생
* 잘못된 토큰을 입력하는 경우, 에러 발생

**Reservation**
* 허용되지 않은 사용자가 예약을 이용할 때, 에러가 발생한다]
* 스케줄이 있는 경우, 예약을 생성할 수 있다.
* 스케줄이 없는 경우, 예약을 생성하면 에러가 발생한다
* 중복 예약을 생성할 경우, 에러가 발생한다
* 예약을 조회할 수 있다
* 예약이 없을 때 예약 목록은 비어있다. 
* 예약을 삭제할 수 있다
* 없는 예약을 삭제할 경우, 에러가 발생한다
* 다른 회원이 삭제하는 경우, 에러가 발생한다

**Schedule**
* 허용되지 않은 사용자가 스케줄을 이용할 때, 에러가 발생한다
* 테마가 있는 경우 스케줄을 생성할 수 있음
* 테마가 없는 스케줄을 생성하는 경우, 에러 발생
* 중복 예약을 생성할 경우, 에러가 발생한다
* 스케줄을 조회할 수 있음
* 예약이 없는 경우 스케줄을 삭제할 수 있음
* 예약이 되어있는 스케줄을 삭제하는 경우, 에러 발생
* 없는 스케줄을 삭제할 경우, 에러 발생

**Theme**
* 허용되지 않은 사용자가 테마를 이용할 때, 에러가 발생한다
* 테마를 생성할 수 있다.
* 중복 테마를 생성할 경우, 에러가 발생한다
* 테마를 조회할 수 있음
* 스케줄이 없는 경우, 테마를 삭제할 수 있다
* 스케줄이 있는데 테마를 삭제하는 경우, 에러 발생
* 없는 테마를 삭제할 경우, 에러가 발생한다