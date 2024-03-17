package com.Schedule.Schedule.auth.controller;

import com.Schedule.Schedule.auth.request.AuthenticationRequest;
import com.Schedule.Schedule.auth.response.AuthenticationResponse;
import com.Schedule.Schedule.auth.request.RegisterRequest;
import com.Schedule.Schedule.service.AuthenticationService;
import com.Schedule.Schedule.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/loginProcess")
    public ResponseEntity<?> AuthenticateAndGetToken(
            @RequestParam (name = "username") String username,
            @RequestParam (name = "password") String password,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        String accessToken = service.authenticate(request).getAccessToken();
        // set accessToken to cookie header
        Cookie jwtCookie = new Cookie("accessToken", accessToken);
        jwtCookie.setHttpOnly(true);  // Set HttpOnly to true for security
        jwtCookie.setSecure(false);    // Set secure to false if not using HTTPS
        jwtCookie.setPath("/");        // Set the cookie path
        jwtCookie.setMaxAge(1800);     // Set the cookie expiration time in seconds

        // Add the cookie to the response
        response.addCookie(jwtCookie);
        response.sendRedirect("/api/v1/shifts");

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
