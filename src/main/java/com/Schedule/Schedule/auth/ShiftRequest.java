package com.Schedule.Schedule.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftRequest {
    private String shiftType;
    private LocalTime startTime;
    private LocalTime endTime;
    private int minimumManagers;
    private int minimumEmployees;

}
