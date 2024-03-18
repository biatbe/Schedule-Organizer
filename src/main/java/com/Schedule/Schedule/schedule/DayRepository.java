package com.Schedule.Schedule.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<List<Day>> findByWeek(Week week);
    Optional<List<Day>> findDaysByDay(DayOfWeek day);
    Optional<Day> findDayByDate(LocalDate date);
}
