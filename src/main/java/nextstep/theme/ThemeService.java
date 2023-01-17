package nextstep.theme;

import nextstep.exception.NotExistEntityException;
import nextstep.theme.dto.ThemeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public Theme findById(Long id) {
        return themeDao.findById(id)
                .orElseThrow(()-> new NotExistEntityException("해당 테마가 존재하지 않습니다."));
    }

    public void delete(Long id) {
        findById(id);
        themeDao.delete(id);
    }
}
