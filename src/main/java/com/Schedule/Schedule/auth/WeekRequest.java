package com.Schedule.Schedule.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private int weekOfYear;
}
