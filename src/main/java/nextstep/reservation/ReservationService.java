package nextstep.reservation;

import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.DuplicateReservationException;
import nextstep.support.exception.NotExistReservationException;
import nextstep.support.exception.NotExistThemeException;
import nextstep.support.exception.NotUserOwnReservationException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(ReservationRequest reservationRequest, String username) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateReservationException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                username);

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NotExistThemeException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, String username) {
        Reservation reservation = reservationDao.findById(id);

        if (reservation == null) {
            throw new NotExistReservationException();
        }
        if (!reservation.getName()
                .equals(username)) {
            throw new NotUserOwnReservationException();
        }

        reservationDao.deleteById(id);
    }

    public void deleteById(Long id) {
        Reservation reservation = reservationDao.findById(id);

        if (reservation == null) {
            throw new NotExistReservationException();
        }

        reservationDao.deleteById(id);
    }
}
