package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.UserScore;
import com.example.English4Kids_Backend.repositories.UserRepository;
import com.example.English4Kids_Backend.repositories.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserScoreRepository userScoreRepository;

    public UserInfo getCurrentUser(Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
            if(currentUser.isPresent()){
                User current = currentUser.get();

                Optional<UserScore> us = userScoreRepository.findByUser(current);
                if (us.isPresent()) {
                    UserScore userScore = us.get();

                    return UserInfo.builder()
                            .email(current.getEmail())
                            .firstName(current.getFirstName())
                            .lastName(current.getLastName())
                            .role(current.getRole())
                            .userScore(userScore)
                            .build();
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
