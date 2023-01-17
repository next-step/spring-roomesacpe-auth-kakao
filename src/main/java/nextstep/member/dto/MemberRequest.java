package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRequest {

    private final String username;
    private final String password;
    private final String name;
    private final String phone;
}
