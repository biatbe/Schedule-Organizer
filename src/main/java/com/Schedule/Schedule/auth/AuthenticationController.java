package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

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

    @PostMapping("/login")
    public String AuthenticateAndGetToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ){
        String accessToken = service.authenticate(authenticationRequest).getAccessToken();
        // set accessToken to cookie header
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(1800)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return "shifts";
    }

}
