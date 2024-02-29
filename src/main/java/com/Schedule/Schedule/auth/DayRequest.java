package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.schedule.Shift;
import com.Schedule.Schedule.schedule.Week;
import com.Schedule.Schedule.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayRequest {
    private DayOfWeek day;
    private Shift shift;
    private Week week;
    private List<User> employees;
}
