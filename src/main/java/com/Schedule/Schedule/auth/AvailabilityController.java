package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.service.AvailabilityService;
import com.Schedule.Schedule.service.JwtService;
import com.Schedule.Schedule.user.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final JwtService jwtService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService, JwtService jwtService) {
        this.availabilityService = availabilityService;
        this.jwtService = jwtService;
    }

    // Endpoint to get availabilities for a specific user
    @GetMapping("/availability")
    public ResponseEntity<List<Availability>> getUserAvailabilities(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractUsername(token);
        List<Availability> userAvailabilities = availabilityService.getUserAvailability(userEmail);
        return ResponseEntity.ok(userAvailabilities);
    }

    // Endpoint to update availability for a user
    @PostMapping("/availability/update")
    public ResponseEntity<String> updateAvailability(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody AvailabilityRequest availabilityRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractUsername(token);
        availabilityService.updateUserAvailability(userEmail, availabilityRequest);
        return ResponseEntity.ok("Availability updated successfully");
    }
}
