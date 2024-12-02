package com.example.English4Kids_Backend.dtos.lessonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLessonRequestDTO {
    private Integer userId;
    private Long lessonId;
    private Double score;
    private String type;
    private int time;
    private LocalDate date;
    private boolean isDone;
}
