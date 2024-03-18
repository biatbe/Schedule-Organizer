package com.Schedule.Schedule.auth.request;

import com.Schedule.Schedule.schedule.Day;
import com.Schedule.Schedule.schedule.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayShiftRequest {
    private Day day;
    private Shift shift;
}
