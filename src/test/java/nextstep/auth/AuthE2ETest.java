package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthE2ETest {
    @Test
    @DisplayName("존재하는 유저는 엑세스 토큰을 발급 받을 수 있다.")
    void test1() {
        TokenResponse tokenResponse = AuthUtil.createTokenForReservationExistUser();
        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저는 엑세스 토큰을 받을 수 없다.")
    void test2() {
        TokenRequest tokenRequest = new TokenRequest("", "");
        AuthUtil.createTokenAndGetValidatableResponse(tokenRequest)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
