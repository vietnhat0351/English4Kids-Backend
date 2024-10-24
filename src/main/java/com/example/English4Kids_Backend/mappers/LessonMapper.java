package com.example.English4Kids_Backend.mappers;

import com.example.English4Kids_Backend.dtos.*;
import com.example.English4Kids_Backend.entities.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LessonMapper {
    public LessonDTO mapToLessonDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .lessonParts(
                        lesson.getLessonParts().stream()
                                .map(this::mapToLessonPartDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public LessonPartDTO mapToLessonPartDTO(LessonPart lessonPart) {
        return LessonPartDTO.builder()
                .id(lessonPart.getId())
                .partNumber(lessonPart.getPartNumber())
                .questions(
                        lessonPart.getQuestions().stream()
                                .map(this::mapToQuestionDTO)
                                .collect(Collectors.toList())
                )
                .stories(lessonPart.getStories().stream()
                        .map(this::mapToStoryDTO) // Assuming there's a similar StoryDTO
                        .collect(Collectors.toList()))
                .build();
    }
    public StoryDTO mapToStoryDTO(Story story) {
        return StoryDTO.builder()
                .id(story.getId())
                .content(story.getContent())
                .build();
    }

    public QuestionDTO mapToQuestionDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .questionType(question.getQuestionType().name())
                .content(question.getContent())
                .audioUrl(question.getAudioUrl())
                .answers(
                        question.getAnswers().stream()
                                .map(this::mapToAnswerDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public AnswerDTO mapToAnswerDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .isCorrect(answer.isCorrect())
                .build();
    }

}
