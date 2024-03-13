package com.Schedule.Schedule.auth;

import com.Schedule.Schedule.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService service;

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public String processLogin(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password
    ) {
        // Authenticate user (implement your authentication logic)
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        AuthenticationResponse response = service.authenticate(request);

        // Include the token in the response header or body
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + response.getToken());

        // Optionally, you can include additional information in the response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", response.getToken());
        responseBody.put("message", "Login successful");

        return "shifts";
    }


}
