package com.Schedule.Schedule.schedule;

import com.Schedule.Schedule.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
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
    @JoinColumn(name = "_week_id")
    private Week week;

    @Column
    private DayOfWeek day;

    @ManyToOne
    @JoinColumn(name = "_shift_id")
    private Shift shift;

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> employeeAssignments = new ArrayList<>();
}
