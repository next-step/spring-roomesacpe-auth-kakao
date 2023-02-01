package nextstep.theme;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ThemeDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Theme> findById(Long id) {
        String sql = "SELECT id, name, desc, price from theme where id = ?;";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findAny();
    }

    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * from theme where name = ?";
        return jdbcTemplate.query(sql, rowMapper, name)
                .stream()
                .findAny();
    }

    public List<Theme> findAll() {
        String sql = "SELECT id, name, desc, price from theme;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM theme where id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
