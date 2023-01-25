package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.RoomEscapeApplication.getPasswordEncoder;
import static nextstep.config.Messages.*;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;


    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse issueToken(TokenRequest tokenRequest) {
        validateMember(tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    private void validateMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        PasswordEncoder passwordEncoder = getPasswordEncoder();
        List<Member> member = memberDao.findByUsername(username);
        if (member.isEmpty()) {
            throw new NullPointerException(MEMBER_NOT_FOUND.getMessage());
        }
        if (passwordEncoder.matches(member.get(0).getPassword(), password)) {
            throw new IllegalArgumentException(PASSWORD_INCORRECT.getMessage());
        }
    }
}
