package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.AuthorizationException;
import nextstep.support.ForbiddenAccessException;
import nextstep.ui.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;
    public final MemberService memberService;

    public ReservationController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<URI> createReservation(@AuthenticationPrincipal String token, @RequestBody ReservationRequest reservationRequest) {
        if (token == null) {
            throw new AuthorizationException();
        }
        memberService.findByToken(token);
        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal String token, @PathVariable Long id) {
        if (token == null) {
            throw new AuthorizationException();
        }

        Member member = memberService.findByToken(token);
        Reservation reservation = reservationService.findById(id);
        if (!reservation.getName().equals(member.getUsername())) {
            throw new ForbiddenAccessException();
        }

        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
