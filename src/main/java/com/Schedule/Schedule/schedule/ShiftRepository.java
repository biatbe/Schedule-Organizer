package com.Schedule.Schedule.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    Shift findByName(String name);
}
