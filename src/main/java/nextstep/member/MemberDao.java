package nextstep.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class MemberDao {
    public final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone")
    );

    public Long save(Member member) {
        String sql = "INSERT INTO member (username, password, name, phone) VALUES (?, HASH('SHA3-512', ?), ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Member findById(Long id) {
        String sql = "SELECT id, username, password, name, phone from member where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Member findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, name, phone from member where username = ? and password = HASH('SHA3-512', ?);";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, username, password);
        } catch (Exception e) {
            return null;
        }
    }
}
