package com.example.English4Kids_Backend.dtos.lessonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLessonRequestDTO {
    private Integer userId;
    private Long lessonId;
    private Double score;
}
