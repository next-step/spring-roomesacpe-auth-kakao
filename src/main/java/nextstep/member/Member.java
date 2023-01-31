package nextstep.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Member {
    private final Long id;
    @NonNull
    private final String username;
    @NonNull
    private final String password;
    @NonNull
    private final String name;
    @NonNull
    private final String phone;
    @NonNull
    @Builder.Default
    private final Role role = Role.MEMBER;

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}

