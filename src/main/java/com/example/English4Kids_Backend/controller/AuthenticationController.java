package com.example.English4Kids_Backend.controller;



import com.example.English4Kids_Backend.dtos.*;
import com.example.English4Kids_Backend.services.AuthenticationService;
import com.example.English4Kids_Backend.services.OtpService;
import com.example.English4Kids_Backend.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final OtpService otpService;

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestBody Email email
            ) throws MessagingException {
        return ResponseEntity.ok(otpService.sendEmail(email.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) throws MessagingException {
        if(!otpService.validate(request.getEmail(), request.getOtp())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
