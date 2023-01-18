package nextstep.auth;

import nextstep.auth.utils.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("secrey-key", 3600000);

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("secrey-key", 3600000);

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }
}