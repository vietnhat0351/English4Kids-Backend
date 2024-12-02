package com.example.English4Kids_Backend.dtos.lessonDTO;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequestAddDTO {
    private Long id;
    private String content;
    private String type;
    private String image;
    private String audio;
    private LessonDTO lesson;
    private VocabularyDTO vocabulary;
    private List<AnswerRequestDTO> answers;

    @Data
    public static class LessonDTO {
        private Long id;
    }

    @Data
    public static class VocabularyDTO {
        private Long id;
    }

    @Data
    public static class AnswerRequestDTO {
        private String content;
        private String image;
        private String audio;
        private Boolean isCorrect;
    }
}
