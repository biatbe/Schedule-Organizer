package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.schedule.Day;
import com.Schedule.Schedule.service.JwtService;
import com.Schedule.Schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final JwtService jwtService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, JwtService jwtService) {
        this.scheduleService = scheduleService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<Day>> getWeek(
            @RequestBody int weekOfYear
    ) throws Exception{
        List<Day> days = scheduleService.getDays(weekOfYear);
        return ResponseEntity.ok(days);
    }
}
