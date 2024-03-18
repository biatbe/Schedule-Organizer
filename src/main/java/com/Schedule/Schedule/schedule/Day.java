package com.Schedule.Schedule.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;

    @ManyToOne
    @JoinColumn(name = "_weekOfYear")
    private Week week;

    @Column
    private LocalDate date;

    @Column
    private DayOfWeek day;

    @OneToMany(mappedBy = "day")
    private List<DayShift> dayShifts;

}
