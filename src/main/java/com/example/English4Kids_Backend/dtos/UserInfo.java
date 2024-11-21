package com.example.English4Kids_Backend.dtos;

import com.example.English4Kids_Backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private Role role;


    private Integer dailyPoints;

    private Integer weeklyPoints;

    private Integer totalPoints;

    private Integer streak; // Continuous streak

    private LocalDate lastLearningDate;
}
