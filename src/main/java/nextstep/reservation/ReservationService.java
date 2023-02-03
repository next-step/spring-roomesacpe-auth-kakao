package nextstep.reservation;

import nextstep.exception.BusinessException;
import nextstep.exception.CommonErrorCode;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    @Transactional
    public Long create(String memberName, ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new BusinessException(CommonErrorCode.SERVER_ERROR);
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new BusinessException(CommonErrorCode.DUPLICATE_ENTITY);
        }

        Reservation newReservation = new Reservation(
                schedule,
                memberName
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new BusinessException(CommonErrorCode.SERVER_ERROR);
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    @Transactional
    public void deleteById(String memberName, Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new BusinessException(CommonErrorCode.NOT_EXIST_ENTITY);
        }
        if (!Objects.equals(reservation.getName(), memberName)) {
            throw new BusinessException(CommonErrorCode.SERVER_ERROR);
        }

        reservationDao.deleteById(id);
    }
}
