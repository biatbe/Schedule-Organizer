package com.Schedule.Schedule.auth.request;

import com.Schedule.Schedule.schedule.Shift;
import com.Schedule.Schedule.schedule.Week;
import com.Schedule.Schedule.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayRequest {
    private LocalDate date;
    private DayOfWeek day;
    private Week week;
    private List<Shift> shift;
    private List<User> employees;
}
