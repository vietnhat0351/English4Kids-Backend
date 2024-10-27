package com.example.English4Kids_Backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonPartDTO {
    private Long id;
    private Integer partNumber;
    private List<QuestionDTO> questions;
    private List<StoryDTO> stories;
}
