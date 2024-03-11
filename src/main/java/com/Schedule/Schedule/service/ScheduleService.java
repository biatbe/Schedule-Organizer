package com.Schedule.Schedule.service;

import com.Schedule.Schedule.auth.DayRequest;
import com.Schedule.Schedule.auth.ShiftRequest;
import com.Schedule.Schedule.auth.WeekRequest;
import com.Schedule.Schedule.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Week week = weekRepository.findWeekByWeekId(weekOfYear)
                .orElseThrow(() -> new Exception("No week found with week number: " + weekOfYear));

        return week;
    }

    public List<Day> getDays(int weekOfYear) throws Exception{
        List<Day> days = dayRepository.findByWeek(this.getWeek(weekOfYear))
                .orElseThrow(() -> new Exception("No week found with week number: " + weekOfYear));

        return days;
    }

    public Shift registerShift(ShiftRequest request) {
        var shift = Shift.builder()
                .shiftType(request.getShiftType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .minimumManagers(request.getMinimumManagers())
                .minimumStaff(request.getMinimumEmployees())
                .build();
        shiftRepository.save(shift);
        return shift;
    }

    public Week registerWeek(WeekRequest request) {
        var week = Week.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        weekRepository.save(week);
        return week;
    }

    public Day registerDay(DayRequest request) {
        var day = Day.builder()
                .day(request.getDay())
                .week(request.getWeek())
                .shift(request.getShift())
                .employeeAssignments(new ArrayList<>()) // or request.getEmployees()
                .build();
        dayRepository.save(day);
        return day;
    }


}
