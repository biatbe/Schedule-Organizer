package com.Schedule.Schedule.schedule;

import com.Schedule.Schedule.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "shiftId")
    private List<Shift> shifts;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> employeeAssignments;

    public void addShift(Shift shift) {
        shifts.add(shift);
    }
}
