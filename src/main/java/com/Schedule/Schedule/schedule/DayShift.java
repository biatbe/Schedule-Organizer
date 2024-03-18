package com.Schedule.Schedule.schedule;

import com.Schedule.Schedule.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class DayShift {

    @Id
    @GeneratedValue
    private Long dayShiftId;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;

    @OneToMany(mappedBy = "id")
    private List<User> users;
}
