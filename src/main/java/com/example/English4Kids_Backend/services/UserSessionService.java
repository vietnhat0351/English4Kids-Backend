package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.UserSessionDTO;
import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.UserSession;
import com.example.English4Kids_Backend.repositories.LessonPartRepository;
import com.example.English4Kids_Backend.repositories.UserRepository;
import com.example.English4Kids_Backend.repositories.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSessionService {
    private final UserRepository userRepository;
    private final LessonPartRepository lessonPartRepository;
    private final UserSessionRepository userSessionRepository;

    public UserSession createUserSession(UserSessionDTO userProgressDTO) {
        Optional<User> user = userRepository.findById(userProgressDTO.getUser().getId());
        if (user.isPresent()) {
            LessonPart lessonPart = lessonPartRepository.findById(userProgressDTO.getLessonPart().getId())
                    .orElseThrow(() -> new IllegalArgumentException("LessonPart not found with id"));

            UserSession userSession = UserSession.builder()
                    .user(user.get())
                    .lessonPart(lessonPart)
                    .sessionDate(LocalDate.now())
                    .questionsAttempted(userProgressDTO.getQuestionsAttempted())
                    .questionsCorrect(userProgressDTO.getQuestionsCorrect())
                    .completed(userProgressDTO.getCompleted())
                    .pointsEarned(userProgressDTO.getPointsEarned())
                    .build();

            return userSessionRepository.save(userSession);
        }
        return null;

    }
}
