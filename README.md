## 🚀 방탈출 인증 관리

### 1단계

#### todo
>> * 토큰 발급하는 API 생성
>> * 내 정보 조회하기
>>   * 토큰을 이용하여 본인 정보 응답하기

#### 구현 목록
- [x] 토큰 발급 API 컨트롤러 구현
- [x] 토큰 발급 API 서비스 구현

### 2단계
#### todo 
>> * 예약하기, 예약취소 개선
>>   * 예약을 할 때, 인증을 하도록 변경
>>   * 비로그인 사용자는 예약이 불가능하다.
>>   * 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

#### 구현 목록
- [x] 로그인 Interceptor 구현
- [x] 로그인 ArgumentResolver 구현
- [x] 커스텀 예외 정의
- [x] 예약을 할 때, 인증을 하도록 변경
- [x] 멤버 로그인 시 예외처리 구현
- [x] 테스트 api 요청 시 토큰 헤더에 추가하도록 수정
- [x] Member 조회 테스트 작성
- [x] 자신의 예약이 아닌 경우 예약 취소가 불가능 하도록 구현
- [x] AuthorizationExtractor 적용
- [x] 인증 방식 변경
- [x] 리팩토링 및 변경된 인증 방식에 따른 코드 변경
- [x] 토큰 유형을 attribute에 set할 수 있도록 변경

### 3단계

#### todo
>> * 관리자 역할을 추가한다.
>>   * 일반 멤버와 관리자 멤버를 구분한다.
>> * 관리자 기능을 보호한다.
>>   * 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
>>   * 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
>> * 그 외 관리자 API는 자유롭게 설계하고 적용한다.

#### 구현 목록
* [x] 멤버에 Role 속성 추가
* [x] MemberDetail 정보를 통해 인증하도록 변경
* [x] admin/user 인터셉터 분리
* [x] 권한에 맞게 접근 경로 설정
* [x] 권한 부족 예외처리 추가
* [x] 멤버 테스트 권한 적용
* [x] 예약 테스트 권한 적용
* [x] 스케줄 테스트 권한 적용
* [x] 테스트 로그인 메소드 분리
* [x] 테마 테스트 권한 적용
* [x] 인증 오류와 API 오류 분리
* [x] @NoAuth 어노테이션 적용

### 리팩토링
* DirtiesTest -> @Sql으로 변경
* Error가 응답 객체를 반환하도록 변경
* 인터셉터 중복 제거
* 1차 피드백 반영
* AdminController 분리