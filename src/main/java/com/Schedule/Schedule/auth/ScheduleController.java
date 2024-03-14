package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.schedule.Day;
import com.Schedule.Schedule.schedule.Week;
import com.Schedule.Schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;


@Controller
@RequestMapping("/api/v1/shifts")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/week")
    public ResponseEntity<?> getShiftsForWeek(
            @RequestParam("week") int week
    ) throws Exception {
        Week weekResult = service.getWeek(week);
        List<Day> days = service.getDays(weekResult);
        return ResponseEntity.ok(days);
    }

    @GetMapping
    public String shifts() {
        return "shifts";
    }

    @GetMapping("/create")
    public String shiftCreate() {
        return "shift-creation";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShift(
            @RequestParam (name = "shiftName") String shiftName,
            @RequestParam (name = "startTime") LocalTime startTime,
            @RequestParam (name = "endTime") LocalTime endTime,
            @RequestParam (name = "minimumStaff") int minimumStaff,
            @RequestParam (name = "minimumManagers") int minimumManagers,
            HttpServletResponse response
    ) throws IOException {
        ShiftRequest request = new ShiftRequest(shiftName, startTime, endTime, minimumManagers, minimumStaff);
        service.registerShift(request);

        response.sendRedirect("/api/v1/shifts");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
