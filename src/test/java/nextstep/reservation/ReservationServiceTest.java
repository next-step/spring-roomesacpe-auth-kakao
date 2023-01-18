package nextstep.reservation;

import java.util.Optional;
import nextstep.schedule.Schedule;
import nextstep.common.exception.NotExistEntityException;
import nextstep.common.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceTest {
    @Mock
    ReservationDao reservationDao;
    @InjectMocks
    ReservationService reservationService;

    @Test
    @DisplayName("존재하지 않는 예약 삭제 불가 테스트")
    void deleteNotExistReservationTest() {
        when(reservationDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reservationService.deleteById(1L, "username"))
                .isInstanceOf(NotExistEntityException.class);
    }

    @Test
    @DisplayName("권한이 없는 예약 삭제 불가 테스트")
    void deleteUnauthorizedReservationTest() {
        Reservation reservation = new Reservation(1L, new Schedule(), "differentUsername");
        when(reservationDao.findById(anyLong())).thenReturn(Optional.of(reservation));
        assertThatThrownBy(() -> reservationService.deleteById(1L, "username"))
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("삭제 성공 테스트")
    void deleteTest() {
        Reservation reservation = new Reservation(1L, new Schedule(), "username");
        when(reservationDao.findById(anyLong())).thenReturn(Optional.of(reservation));
        assertThatCode(() -> reservationService.deleteById(1L, "username")).doesNotThrowAnyException();
    }
}
