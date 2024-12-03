package com.example.English4Kids_Backend.dtos.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProgressDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer totalLessonsLearned;
    private Integer totalVocabulariesLearned;
}
