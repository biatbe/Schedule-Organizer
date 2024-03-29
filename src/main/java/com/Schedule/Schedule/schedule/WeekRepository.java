package com.Schedule.Schedule.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeekRepository extends JpaRepository<Week, Integer> {
    Optional<Week> findWeekByWeekOfYear(int weekId);
}
