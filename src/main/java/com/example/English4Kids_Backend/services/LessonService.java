package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.lessonDTO.*;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.UserLessonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<Long> completedLessonIds = userLessonRepository.findLessonIdsByUserId(userId);

        // Map each lesson to the DTO, including vocabularies and questions
        return lessons.stream()
                .map(lesson -> new LessonCompletionDTO(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getDescription(),
                        lesson.getImage(),
                        completedLessonIds.contains(lesson.getId()), // Check if completed
                        // Map vocabularies to VocabularyDTO
                        lesson.getVocabularies().stream()
                                .map(vocab -> new VocabularyDTO(
                                        vocab.getId(),
                                        vocab.getWord(),
                                        vocab.getMeaning(), vocab.getPronunciation(), vocab.getType(),
                                        vocab.getAudio(),
                                        vocab.getImage()
                                       ))
                                .collect(Collectors.toList()),

                        lesson.getQuestions().stream()
                                .map(question -> new QuestionDTO(
                                        question.getId(),
                                        question.getContent(),
                                        question.getType(),
                                        question.getImage(),
                                        question.getAudio(),
                                        question.getAnswers().stream()
                                                .map(answer -> new AnswerDTO(
                                                        answer.getId(),
                                                        answer.getContent(),
                                                        answer.getImage(),
                                                        answer.getAudio(),
                                                        answer.getIsCorrect()
                                                ))
                                                .collect(Collectors.toList())

                                        , question.getVocabulary() == null ? null : new VocabularyDTO(
                                                question.getVocabulary().getId(),
                                                question.getVocabulary().getWord(),
                                                question.getVocabulary().getMeaning(),
                                                question.getVocabulary().getPronunciation(),
                                                question.getVocabulary().getType(),
                                                question.getVocabulary().getAudio(),
                                                question.getVocabulary().getImage())

                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonById(long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));
        return new LessonDTO(lesson);
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
}
