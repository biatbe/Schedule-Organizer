package com.Schedule.Schedule.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    @Column(unique = true)
    private String shiftType; // open, mid-day, close

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    private int minimumManagers;

    @Column
    private int minimumStaff;

    @ManyToMany
    @JoinTable(
            name = "day_shift",
            joinColumns = @JoinColumn(name = "shift_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id")
    )
    private List<Day> days;

    public void addDay(Day day) {
        days.add(day);
    }
}
