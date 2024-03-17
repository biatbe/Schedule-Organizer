package com.Schedule.Schedule.service;

import com.Schedule.Schedule.auth.request.DayRequest;
import com.Schedule.Schedule.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftService {

    private final WeekRepository weekRepository;
    private final DayRepository dayRepository;
    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(WeekRepository weekRepository, DayRepository dayRepository, ShiftRepository shiftRepository) {
        this.weekRepository = weekRepository;
        this.dayRepository = dayRepository;
        this.shiftRepository = shiftRepository;
    }

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public void registerDay(DayRequest request) {
        var day = Day.builder()
                .date(request.getDate())
                .week(request.getWeek())
                .day(request.getDay())
                .shifts(new ArrayList<>())
                .employeeAssignments(new ArrayList<>())
                .build();

        dayRepository.save(day);
    }

    public void assignShiftToSpecificDate(Long shiftId, LocalDate date) {
        Day day;
        if (dayRepository.getDayByDate(date).isPresent()) {
            day = dayRepository.getDayByDate(date).get();
        } else {
            DayRequest request = new DayRequest(date, date.getDayOfWeek(),
                    weekRepository.findWeekByWeekOfYear(date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)).get(),
                    null, null);
            registerDay(request);
            day = dayRepository.getDayByDate(date).get();
        }
        Shift shift = shiftRepository.findByShiftId(shiftId).get();
        List<Shift> shifts = day.getShifts();
        if (!shifts.contains(shift)) {
            day.addShift(shift);
        }
    }

    public void assignShiftToSpecificDay(Long shiftId, DayOfWeek day) {
        List<Day> days = dayRepository.findDaysByDay(day).get();
        Shift shift = shiftRepository.findByShiftId(shiftId).get();
        for (Day d : days) {
            List<Shift> shifts = d.getShifts();
            if (!shifts.contains(shift)) {
                d.addShift(shift);
            }
        }
    }

}
