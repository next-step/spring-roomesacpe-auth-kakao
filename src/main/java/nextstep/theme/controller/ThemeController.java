package nextstep.theme.controller;

import nextstep.theme.domain.Theme;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.mapper.ThemeMapper;
import nextstep.theme.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());

        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> showThemes() {
        List<Theme> themes = themeService.findAll();

        return ResponseEntity.ok().body(ThemeMapper.INSTANCE.domainListToResponseDtoList(themes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
