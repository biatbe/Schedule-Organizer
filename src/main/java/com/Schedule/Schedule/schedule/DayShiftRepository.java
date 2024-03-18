package com.Schedule.Schedule.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayShiftRepository extends JpaRepository<DayShift, Long> {
    Optional<DayShift> findAllByDay(Day day);
    Optional<DayShift> findDayShiftByDayAndShift(Day day, Shift shift);
    Optional<DayShift> findByDayShiftId(Long id);
}
