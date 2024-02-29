package com.Schedule.Schedule.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
}
