package com.example.English4Kids_Backend.controller;



import com.example.English4Kids_Backend.dtos.*;
import com.example.English4Kids_Backend.entities.Otp;
import com.example.English4Kids_Backend.services.AuthenticationService;
import com.example.English4Kids_Backend.services.OtpService;
import com.example.English4Kids_Backend.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/login")
    public ResponseEntity<UserInfo> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }


    @PostMapping("/register")
    public ResponseEntity<AuthedResponse> register(
            @RequestBody RegisterRequest request
    ) throws MessagingException {
        if(!otpService.validate(request.getEmail(), request.getOtp())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login1")
    public ResponseEntity<AuthedResponse> login(
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
