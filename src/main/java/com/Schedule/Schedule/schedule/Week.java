package com.Schedule.Schedule.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_week")
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weekId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(unique = true)
    private int weekOfYear;

    public Week(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        this.weekOfYear = startDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

}
