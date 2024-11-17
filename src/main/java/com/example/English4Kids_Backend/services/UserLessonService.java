package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.lessonDTO.UserLessonRequestDTO;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.UserLesson;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.UserLessonRepository;
import com.example.English4Kids_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLessonService {
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;

    public void addOrUpdateUserLesson(UserLessonRequestDTO dto) {
        Optional<UserLesson> existingUserLesson = userLessonRepository.findByUserIdAndLessonId(dto.getUserId(), dto.getLessonId());

        if (existingUserLesson.isPresent()) {
            UserLesson userLesson = existingUserLesson.get();


            if (dto.getScore() > userLesson.getScore()) {
                userLesson.setScore(dto.getScore());
                userLessonRepository.save(userLesson);
            }
        } else {
            UserLesson newUserLesson = UserLesson.builder()
                    .user(User.builder().id(dto.getUserId()).build())
                    .lesson(Lesson.builder().id(dto.getLessonId()).build())
                    .score(dto.getScore())
                    .build();
            userLessonRepository.save(newUserLesson);
        }
    }
}