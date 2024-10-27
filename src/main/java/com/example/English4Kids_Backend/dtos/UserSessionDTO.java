package com.example.English4Kids_Backend.dtos;

import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDTO {

    private User user;

    private LessonPart lessonPart;

    private LocalDate sessionDate;

    private Integer questionsAttempted;

    private Integer questionsCorrect;

    private Boolean completed;

    private Integer pointsEarned;
}
