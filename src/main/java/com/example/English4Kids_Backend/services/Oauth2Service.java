package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.AuthResponse;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.English4Kids_Backend.entities.Role.USER;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse authenticate(OAuth2User oAuth2User) {
        // kiểm tra xem user đã tồn tại trong db chưa nếu chưa thì thêm vào
        Optional<User> currentUser = userRepository.findByEmail(oAuth2User.getAttribute("email"));

        AuthResponse authenticationResponse;
        if (currentUser.isEmpty()) {
            User user = User.builder()
                    .email(oAuth2User.getAttribute("email"))
                    .firstName(oAuth2User.getAttribute("given_name"))
                    .lastName(oAuth2User.getAttribute("family_name"))
                    .dailyPoints(0)
                    .lastLearningDate(LocalDate.now())
                    .streak(0)
                    .weeklyPoints(0)
                    .totalPoints(0)
                    .role(USER)
                    .build();
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            authenticationResponse = AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            var jwtToken = jwtService.generateToken(currentUser.get());
            var refreshToken = jwtService.generateRefreshToken(currentUser.get());
            authenticationResponse = AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return authenticationResponse;
    }
}
