package nextstep.reservation;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.DuplicateEntityException;
import nextstep.support.LoginException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;
    private final MemberDao memberDao;

    public Long create(ReservationRequest reservationRequest, Long memberId) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }
        Member member = memberDao.findById(memberId);
        if (member == null) {
            throw new LoginException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                member
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long reservationId, Long memberId) {
        Reservation reservation = reservationDao.findById(reservationId);
        if (reservation == null) {
            throw new NullPointerException();
        }
        Member member = memberDao.findById(memberId);
        if (!Objects.equals(reservation.getMember().getId(), member.getId())) {
            throw new RuntimeException("자신의 예약만 취소할 수 있습니다.");
        }

        reservationDao.deleteById(reservationId);
    }
}
