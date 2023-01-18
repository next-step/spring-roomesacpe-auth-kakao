package nextstep.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.MemberRequest;
import nextstep.dto.request.ScheduleRequest;
import nextstep.dto.request.ThemeRequest;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.common.fixture.MemberProvider.로그인을_한다;
import static nextstep.common.fixture.MemberProvider.멤버를_생성한다;
import static nextstep.common.fixture.ScheduleProvider.스케줄을_생성한다;
import static nextstep.common.fixture.ThemeProvider.테마를_생성한다;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    @Test
    void 멤버_생성에_성공한다() {
        // given
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");

        // when
        ExtractableResponse<Response> response = 멤버를_생성한다(memberRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 로그인되어_있지_않은_경우_멤버_정보_조회에_실패한다() {
        // given
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")

        // when
        .when()
                .get("/members/me")

        // then
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 멤버의_정보를_조회한다() {
        // given
        String username = "username", password = "password";
        멤버를_생성한다(new MemberRequest(username, password, "name", "010-0000-0000"));
        TokenResponse response = 로그인을_한다(new TokenRequest(username, password));

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getAccessToken())

        // when
        .when()
                .get("/members/me")

        // then
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("username", equalTo(username))
                .body("password", equalTo(password));
    }

    @Test
    void 일반_사용자는_테마를_생성할_수_없다() {
        // given
        String username = "username", password = "password";
        멤버를_생성한다(new MemberRequest(username, password, "name", "010-0000-0000"));
        TokenResponse userTokenResponse = 로그인을_한다(new TokenRequest(username, password));
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 20000);

        // when
        ExtractableResponse<Response> response = 테마를_생성한다(userTokenResponse.getAccessToken(), themeRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 일반_사용자는_테마를_삭제할_수_없다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        ExtractableResponse<Response> response = 테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 20000));

        String username = "username", password = "password";
        멤버를_생성한다(new MemberRequest(username, password, "name", "010-0000-0000"));
        TokenResponse userTokenResponse = 로그인을_한다(new TokenRequest(username, password));

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userTokenResponse.getAccessToken())

        // when
        .when()
                .delete("/admin" + response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 일반_사용자는_스케줄을_생성할_수_없다() {
        // given
        String username = "username", password = "password";
        멤버를_생성한다(new MemberRequest(username, password, "name", "010-0000-0000"));
        TokenResponse userTokenResponse = 로그인을_한다(new TokenRequest(username, password));
        ScheduleRequest scheduleRequest = new ScheduleRequest(1L, "2023-01-18", "13:00");

        // when
        ExtractableResponse<Response> response = 스케줄을_생성한다(userTokenResponse.getAccessToken(), scheduleRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 일반_사용자는_스케줄을_삭제할_수_없다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 20000));
        ExtractableResponse<Response> response = 스케줄을_생성한다(adminTokenResponse.getAccessToken(), new ScheduleRequest(1L, "2023-01-18", "13:00"));

        String username = "username", password = "password";
        멤버를_생성한다(new MemberRequest(username, password, "name", "010-0000-0000"));
        TokenResponse userTokenResponse = 로그인을_한다(new TokenRequest(username, password));

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userTokenResponse.getAccessToken())

        // when
        .when()
                .delete("/admin" + response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
