package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(String username, String password) {
        validateTokenRequest(username, password);

        String accessToken = jwtTokenProvider.createToken(username);
        return new TokenResponse(accessToken);
    }

    private void validateTokenRequest(String username, String password) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NullPointerException("유저가 존재하지 않습니다.");
        }
        if (member.checkWrongPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
