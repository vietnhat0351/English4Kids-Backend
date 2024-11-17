package com.example.English4Kids_Backend.dtos.lessonDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonCompletionDTO {
    private long id;
    private String title;
    private String description;
    private String image;
    private boolean completed; // true if the user has completed the lesson
    private List<VocabularyDTO> vocabularies;
    private List<QuestionDTO> questions;


}
