package com.Schedule.Schedule.service;

import com.Schedule.Schedule.auth.DayRequest;
import com.Schedule.Schedule.auth.ShiftRequest;
import com.Schedule.Schedule.auth.WeekRequest;
import com.Schedule.Schedule.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Service
public class ScheduleService {

    private final WeekRepository weekRepository;
    private final DayRepository dayRepository;
    private final ShiftRepository shiftRepository;

    @Autowired
    public ScheduleService(WeekRepository weekRepository, DayRepository dayRepository, ShiftRepository shiftRepository) {
        this.weekRepository = weekRepository;
        this.dayRepository = dayRepository;
        this.shiftRepository = shiftRepository;
    }

    public Week getWeek(int weekOfYear) throws Exception{
        Week week;
        if (weekRepository.findWeekByWeekOfYear(weekOfYear).isPresent()) {
            week = weekRepository.findWeekByWeekOfYear(weekOfYear).get();

        } else {
            int year = LocalDate.now().getYear();
            LocalDate januaryFirst = LocalDate.of(year, 1, 1);
            LocalDate firstMonday = januaryFirst.with(firstInMonth(DayOfWeek.MONDAY));
            int dayOfFirstMonday = firstMonday.getDayOfYear();
            int startDayOfWeek = 1;
            int endDayofWeek = 1;
            if (dayOfFirstMonday == 1) {
                startDayOfWeek = (int) (1 + (weekOfYear - 1) * 7);
            } else {
                startDayOfWeek = (int) (1 + (weekOfYear - 2) * 7);
            }
            endDayofWeek = startDayOfWeek + 6;
            LocalDate startDate = LocalDate.ofYearDay(year, startDayOfWeek);
            LocalDate endDate = LocalDate.ofYearDay(year, endDayofWeek);
            week = registerWeek(new WeekRequest(startDate, endDate, weekOfYear));
            for (int i = startDayOfWeek; i <= endDayofWeek; i++) {
                LocalDate day = LocalDate.ofYearDay(year, i);
                DayRequest request = new DayRequest(day.getDayOfWeek(), null, week, null);
                registerDayByDefault(request);
            }
        }

        return week;
    }

    public List<Day> getDays(Week week) throws Exception{
        return dayRepository.findByWeek(week)
                .orElseThrow(() -> new Exception("No week found with week number: " + week));
    }

    public void registerShift(ShiftRequest request) {
        var shift = Shift.builder()
                .shiftType(request.getShiftType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .minimumManagers(request.getMinimumManagers())
                .minimumStaff(request.getMinimumEmployees())
                .build();
        shiftRepository.save(shift);
    }

    public Week registerWeek(WeekRequest request) {
        var week = Week.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .weekOfYear(request.getWeekOfYear())
                .build();
        weekRepository.save(week);
        return week;
    }

    public void registerDayByDefault(DayRequest request) {
        var day = Day.builder()
                .day(request.getDay())
                .week(request.getWeek())
                //.employeeAssignments(new ArrayList<>()) // or request.getEmployees()
                .build();
        dayRepository.save(day);
    }

    public Day registerDay(DayRequest request) {
        var day = Day.builder()
                .day(request.getDay())
                .week(request.getWeek())
                .shift(request.getShift())
                //.employeeAssignments(new ArrayList<>()) // or request.getEmployees()
                .build();
        dayRepository.save(day);
        return day;
    }
}
