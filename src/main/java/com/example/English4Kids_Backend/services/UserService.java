package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.ChangePasswordRequest;
import com.example.English4Kids_Backend.dtos.UserInfo;
import com.example.English4Kids_Backend.entities.Role;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo getCurrentUser(Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
            if (currentUser.isPresent()) {
                User current = currentUser.get();
                return UserInfo.builder()
                        .id(Long.valueOf(current.getId()))
                        .email(current.getEmail())
                        .firstName(current.getFirstName())
                        .lastName(current.getLastName())
                        .avatar(current.getAvatar())
                        .role(current.getRole())
                        .dailyPoints(current.getDailyPoints())
                        .weeklyPoints(current.getWeeklyPoints())
                        .totalPoints(current.getTotalPoints())
                        .streak(current.getStreak())
                        .lastLearningDate(current.getLastLearningDate())
                        .build();
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public UserInfo updateUserPoint(UserInfo userUpdate) {
        Optional<User> currentUser = userRepository.findById(Math.toIntExact(userUpdate.getId()));
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            user.setDailyPoints(userUpdate.getDailyPoints());
            user.setWeeklyPoints(userUpdate.getWeeklyPoints());
            user.setTotalPoints(userUpdate.getTotalPoints());
            user.setLastLearningDate(userUpdate.getLastLearningDate());
            user.setStreak(userUpdate.getStreak());

            userRepository.save(user);
        }

        return userUpdate;
    }

    public List<UserInfo> getUesrRanking() {
        List<UserInfo> userInfoList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserInfo userInfo = UserInfo.builder()
                    .id(Long.valueOf(user.getId()))
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .dailyPoints(user.getDailyPoints())
                    .weeklyPoints(user.getWeeklyPoints())
                    .totalPoints(user.getTotalPoints())
                    .streak(user.getStreak())
                    .lastLearningDate(user.getLastLearningDate())
                    .build();
            if (userInfo.getRole().equals(Role.ADMIN)) continue;
            userInfoList.add(userInfo);
        }
        return userInfoList;

    }

    public UserInfo updateUserInfo(UserInfo userUpdate) {
        Optional<User> currentUser = userRepository.findById(Math.toIntExact(userUpdate.getId()));
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            user.setFirstName(userUpdate.getFirstName());
            user.setLastName(userUpdate.getLastName());
            user.setAvatar(userUpdate.getAvatar());
            userRepository.save(user);
        }
        return userUpdate;

    }

    public String changePassword(ChangePasswordRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) return "User not found";
        if (passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(currentUser);
            return "Password changed successfully";
        } else {
            throw new RuntimeException("Old password is incorrect");
        }

    }
};
