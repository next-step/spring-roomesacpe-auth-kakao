package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class AdminThemeE2ETest {

    private String token;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("1", true);
    }

    @DisplayName("테마를 생성한다")
    @Test
    public void create() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().get("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().delete("/admin/themes/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
