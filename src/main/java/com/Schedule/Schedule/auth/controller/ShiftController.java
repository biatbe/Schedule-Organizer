package com.Schedule.Schedule.auth.controller;

import com.Schedule.Schedule.schedule.Shift;
import com.Schedule.Schedule.service.ShiftService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("/assign-shifts")
    public String showAssignShiftsPage(Model model) {
        List<Shift> shifts = shiftService.getAllShifts();
        model.addAttribute("shifts", shifts);
        return "assign-shifts";
    }

    @PostMapping("/assign-shifts")
    public ResponseEntity<?> assignShifts(
            @RequestParam("shift") Long shiftId,
            @RequestParam(value = "specificDay", required = false) LocalDate specificDay,
            @RequestParam(value = "dayOfWeek", required = false) DayOfWeek dayOfWeek,
            HttpServletResponse response
    ) throws IOException {
        if (specificDay != null) {
            // Assign shift to specific day
            shiftService.assignShiftToSpecificDate(shiftId, specificDay);
        } else if (dayOfWeek != null) {
            // Assign shift to day of the week
            shiftService.assignShiftToSpecificDay(shiftId, dayOfWeek);
        }
        response.sendRedirect("/api/v1/assign-shifts");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
