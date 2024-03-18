package com.Schedule.Schedule.auth.controller;

import com.Schedule.Schedule.service.AvailabilityService;
import com.Schedule.Schedule.service.JwtService;
import com.Schedule.Schedule.user.Availability;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AvailabilityController {

    @GetMapping("/availability")
    public String getAvailability() {
        return "availability";
    }

    @PostMapping("/availability")
    public ResponseEntity<?> getUserAvailabilities(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }
        headers.add("Authorization", "Bearer " + accessToken);
        System.out.println(headers);
        response.sendRedirect("/api/v1/availability");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
