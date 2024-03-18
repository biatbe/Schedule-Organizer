package com.Schedule.Schedule.service;

import com.Schedule.Schedule.auth.request.DayRequest;
import com.Schedule.Schedule.auth.request.DayShiftRequest;
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
    private final DayShiftRepository dayShiftRepository;

    @Autowired
    public ShiftService(WeekRepository weekRepository, DayRepository dayRepository, ShiftRepository shiftRepository,
                        DayShiftRepository dayShiftRepository) {
        this.weekRepository = weekRepository;
        this.dayRepository = dayRepository;
        this.shiftRepository = shiftRepository;
        this.dayShiftRepository = dayShiftRepository;
    }

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Day getDay(LocalDate date) {
        if (dayRepository.findDayByDate(date).isEmpty()) {
            DayRequest request = new DayRequest(date, date.getDayOfWeek(),
                    weekRepository.findWeekByWeekOfYear(date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)).get(),
                    null, null);
            registerDay(request);
        }
        return dayRepository.findDayByDate(date).get();
    }


    public void registerDay(DayRequest request) {
        var day = Day.builder()
                .date(request.getDate())
                .week(request.getWeek())
                .day(request.getDay())
                .dayShifts(new ArrayList<>())
                .build();

        dayRepository.save(day);
    }

    public void registerShift(DayShiftRequest request) {
        if (dayShiftRepository.findDayShiftByDayAndShift(request.getDay(), request.getShift()).isPresent()) {
            System.out.println("Shift is already present on this day");
            return;
        }
        var dayShift = DayShift.builder()
                .day(request.getDay())
                .shift(request.getShift())
                .build();
        dayShiftRepository.save(dayShift);
        request.getDay().getDayShifts().add(dayShift);
        request.getShift().getDayShifts().add(dayShift);
    }

    public void assignShiftToSpecificDate(Long shiftId, LocalDate date) {
        Day day;
        Shift shift = shiftRepository.findByShiftId(shiftId).get();
        if (dayRepository.findDayByDate(date).isEmpty()) {
            DayRequest request = new DayRequest(date, date.getDayOfWeek(),
                    weekRepository.findWeekByWeekOfYear(date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)).get(),
                    null, null);
            registerDay(request);
        }
        day = dayRepository.findDayByDate(date).get();
        DayShiftRequest request = new DayShiftRequest(day, shift);
        registerShift(request);
    }

    public void assignShiftToSpecificDay(Long shiftId, DayOfWeek day) {
        List<Day> days = dayRepository.findDaysByDay(day).get();
        Shift shift = shiftRepository.findByShiftId(shiftId).get();
        for (Day d : days) {
            DayShiftRequest request = new DayShiftRequest(d, shift);
            registerShift(request);
        }
    }

    public void removeDayShift(Long dayShiftId) {
        if (dayShiftRepository.findByDayShiftId(dayShiftId).isEmpty()) {
            System.out.println("Shift does not exist!");
            return;
        }
        DayShift dayShift = dayShiftRepository.findByDayShiftId(dayShiftId).get();
        dayShiftRepository.delete(dayShift);
        dayShift.getDay().getDayShifts().remove(dayShift);
        dayShift.getShift().getDayShifts().remove(dayShift);
    }

}
