package com.example.English4Kids_Backend.dtos.lessonDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private Double score;
    private String type;
    private LocalDate date;
    private int time;
    private boolean isDone;
    private List<VocabularyDTO> vocabularies;
    private List<QuestionDTO> questions;


}
