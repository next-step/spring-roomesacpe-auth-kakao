package nextstep.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberRequest {
    private final String username;
    private final String password;
    private final String name;
    private final String phone;

    public Member toEntity() {
        return new Member(username, password, name, phone);
    }
}
