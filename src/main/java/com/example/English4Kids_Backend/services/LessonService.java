package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.lessonDTO.*;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.UserLesson;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.UserLessonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;

    public List<LessonDTO> getAllLessons() {
        try {
            List<Lesson> lessons = lessonRepository.findAll();
            return lessons.stream()
                    .map(LessonDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public List<LessonCompletionDTO> getLessonsWithCompletionStatus(Long userId) {

        List<Lesson> lessons = lessonRepository.findAll();

        AtomicReference<Double> score = new AtomicReference<>((double) 0);
        LocalDate date = LocalDate.now();
        AtomicInteger time = new AtomicInteger();
        AtomicBoolean isDone = new AtomicBoolean(false);

        return lessons.stream().map(lesson -> {
            // Tìm UserLesson tương ứng (nếu có)
            List<UserLesson> userLessonOpt = userLessonRepository.findByUserIdAndLessonId(Math.toIntExact(userId), lesson.getId());
            for (UserLesson userLesson : userLessonOpt) {
                if (userLessonOpt.isEmpty()) {
                    continue;
                }
                if (userLesson.getScore() > score.get()) {
                    score.set(userLesson.getScore());
                }
                if (userLesson.getTime() < time.get()) {
                    time.set(userLesson.getTime());
                }
                if (userLesson.isDone()) {
                    isDone.set(true);
                }
            }

            // Tạo DTO
            return LessonCompletionDTO.builder()
                    .id(lesson.getId())
                    .title(lesson.getTitle())
                    .description(lesson.getDescription())
                    .image(lesson.getImage())
                    .score(score.get())
                    .type("")
                    .date(date)
                    .time(time.get())
                    .isDone(isDone.get())
                    .vocabularies(lesson.getVocabularies().stream()
                            .map(vocabulary -> VocabularyDTO.builder()
                                    .id(vocabulary.getId())
                                    .word(vocabulary.getWord())
                                    .meaning(vocabulary.getMeaning())
                                    .image(vocabulary.getImage())
                                    .audio(vocabulary.getAudio())
                                    .type(vocabulary.getType())
                                    .pronunciation(vocabulary.getPronunciation())
                                    .build())
                            .collect(Collectors.toList()))
                    .questions(lesson.getQuestions().stream()
                            .map(question -> QuestionDTO.builder()
                                    .id(question.getId())
                                    .content(question.getContent())
                                    .image(question.getImage())
                                    .audio(question.getAudio())

                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());


    }

    public LessonDTO getLessonById(long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));
        return new LessonDTO(lesson);
    }

    public LessonCompletionDTO getLessonByIdUser(long id, long userId) {

        AtomicReference<Double> score = new AtomicReference<>((double) 0);
        LocalDate date = LocalDate.now();
        AtomicInteger time = new AtomicInteger();
        AtomicBoolean isDone = new AtomicBoolean(false);

        List<UserLesson> userLessonOpt = userLessonRepository.findByUserIdAndLessonId(Math.toIntExact(userId), id);
        for (UserLesson userLesson : userLessonOpt) {
            if (userLessonOpt.isEmpty()) {
                continue;
            }
            if (userLesson.getScore() > score.get()) {
                score.set(userLesson.getScore());
            }
            if (userLesson.getTime() < time.get()) {
                time.set(userLesson.getTime());
            }
            if (userLesson.isDone()) {
                isDone.set(true);
            }
        }

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));

        return LessonCompletionDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .image(lesson.getImage())
                .score(score.get())
                .type("")
                .date(date)
                .time(time.get())
                .isDone(isDone.get())
                .vocabularies(lesson.getVocabularies().stream()
                        .map(vocabulary -> VocabularyDTO.builder()
                                .id(vocabulary.getId())
                                .word(vocabulary.getWord())
                                .meaning(vocabulary.getMeaning())
                                .image(vocabulary.getImage())
                                .audio(vocabulary.getAudio())
                                .type(vocabulary.getType())
                                .pronunciation(vocabulary.getPronunciation())
                                .build())
                        .collect(Collectors.toList()))
                .questions(lesson.getQuestions().stream()
                        .map(question -> QuestionDTO.builder()
                                .id(question.getId())
                                .content(question.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();


    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        Lesson lesson = Lesson.builder()
                .title(lessonDTO.getTitle())
                .description(lessonDTO.getDescription())
                .image(lessonDTO.getImage())
                .build();
        Lesson savedLesson = lessonRepository.save(lesson);
        return new LessonDTO(savedLesson);
    }

    public void deleteLesson(long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        lessonRepository.delete(lesson);
    }

    public Lesson updateLesson(Lesson lesson) {
        Lesson currentLesson = lessonRepository.findById(lesson.getId())
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        currentLesson.setTitle(lesson.getTitle());
        currentLesson.setDescription(lesson.getDescription());
        currentLesson.setImage(lesson.getImage());
        return lessonRepository.save(currentLesson);

    }
}
