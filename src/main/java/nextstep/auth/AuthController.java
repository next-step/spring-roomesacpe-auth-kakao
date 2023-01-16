package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest request){
        Optional<Member> member = memberService.getByNameAndPassword(request.getUsername(), request.getPassword());
        TokenResponse response  = authService.createToken(member);
        return ResponseEntity.ok(response);
    }

}
