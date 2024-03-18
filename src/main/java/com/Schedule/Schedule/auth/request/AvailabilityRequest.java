package com.Schedule.Schedule.auth.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityRequest {

    private Long userId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String role;

}
