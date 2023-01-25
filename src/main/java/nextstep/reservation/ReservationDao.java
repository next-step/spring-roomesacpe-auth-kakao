package nextstep.reservation;

import nextstep.Mapper.DatabaseMapper;
import nextstep.Mapper.H2Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final DatabaseMapper databaseMapper;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.databaseMapper = new H2Mapper();
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (schedule_id, name) VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, reservation.getSchedule().getId());
            ps.setString(2, reservation.getName());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAllByThemeIdAndDate(String username, Long themeId, String date) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.name, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "from reservation " +
                "inner join schedule on reservation.schedule_id = schedule.id " +
                "inner join theme on schedule.theme_id = theme.id " +
                "where reservation.name = ? and theme.id = ? and schedule.date = ?;";

        return jdbcTemplate.query(sql, databaseMapper.reservationRowMapper(), username, themeId, Date.valueOf(date));
    }

    public Reservation findById(Long id) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.name, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "from reservation " +
                "inner join schedule on reservation.schedule_id = schedule.id " +
                "inner join theme on schedule.theme_id = theme.id " +
                "where reservation.id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, databaseMapper.reservationRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Reservation> findByScheduleId(Long id) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.name, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "from reservation " +
                "inner join schedule on reservation.schedule_id = schedule.id " +
                "inner join theme on schedule.theme_id = theme.id " +
                "where schedule.id = ?;";

        try {
            return jdbcTemplate.query(sql, databaseMapper.reservationRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation where id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void findByName(String username) {
        String sql = "SELECT name FROM reservation WHERE name = ?";

    }
}
