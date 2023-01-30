package nextstep.schedule;

import nextstep.auth.annotation.LoginRequired;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ThemeService themeService;

    public ScheduleController(ScheduleService scheduleService, ThemeService themeService) {
        this.scheduleService = scheduleService;
        this.themeService = themeService;
    }

    @PostMapping
    @LoginRequired
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(
                scheduleRequest.toEntityWithTheme(
                        themeService.findById(scheduleRequest.getThemeId())
                )
        );
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping
    @LoginRequired
    public ResponseEntity<List<Schedule>> showSchedules(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/{id}")
    @LoginRequired
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
