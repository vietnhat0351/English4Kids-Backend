package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.AuthResponse;
import com.example.English4Kids_Backend.dtos.LoginRequest;
import com.example.English4Kids_Backend.dtos.RegisterRequest;
import com.example.English4Kids_Backend.entities.Role;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .email(request.getEmail())
                    .dailyPoints(0)
                    .avatar("https://assets.quizlet.com/static/i/animals/108.3b3090077134db3.jpg")
                    .weeklyPoints(0)
                    .joinDate(LocalDate.now())
                    .totalPoints(0)
                    .streak(0)
                    .lastLearningDate(LocalDate.now())
                    .build();
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(savedUser);
            var refreshToken = jwtService.generateRefreshToken(savedUser);

            return AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }catch (Exception e){
            return null;
        }
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            return AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {

            return null;
        }

    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken)) {
              var accessToken = jwtService.generateToken(user);
              var authenResponse = AuthResponse.builder()
                      .accessToken(accessToken)
                      .refreshToken(refreshToken)
                      .build();
              new ObjectMapper().writeValue(response.getOutputStream(), authenResponse);
            }
        }
    }
}
