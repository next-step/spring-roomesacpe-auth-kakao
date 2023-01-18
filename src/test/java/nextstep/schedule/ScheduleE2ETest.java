package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.domain.model.request.ScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static nextstep.auth.LoginUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ScheduleE2ETest {
    private String token;
    @Test
    @DisplayName("스케줄을 생성한다")
    public void createSchedule() {
        token = loginAdmin();

        ScheduleRequest body = new ScheduleRequest(9999L, "2022-08-15", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("스케줄을 조회한다")
    public void showSchedules() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .param("themeId", 9999L)
                .param("date", "2022-08-11")
                .auth().oauth2(token)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @Test
    @DisplayName("스케줄을 삭제한다")
    void delete() {
        token = loginAdmin();
        String location = requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin" + location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(9999L, "2022-08-15", "13:00");
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
