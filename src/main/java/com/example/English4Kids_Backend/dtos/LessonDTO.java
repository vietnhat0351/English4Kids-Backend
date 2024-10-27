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
public class LessonDTO {
    private Long id;
    private String title;
    private String description;
    private List<LessonPartDTO> lessonParts;
}
