package com.example.English4Kids_Backend.dtos.lessonDTO;


import com.example.English4Kids_Backend.entities.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDTO {
    private long id;
    private String title;
    private String description;
    private String image;
    private List<QuestionDTO> questions;
    private List<VocabularyDTO> vocabularies;

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
        this.image = lesson.getImage();
        this.questions = lesson.getQuestions().stream()
                .map(QuestionDTO::new)
                .collect(Collectors.toList());
        this.vocabularies = lesson.getVocabularies().stream()
                .map(VocabularyDTO::new)
                .collect(Collectors.toList());
    }
}
