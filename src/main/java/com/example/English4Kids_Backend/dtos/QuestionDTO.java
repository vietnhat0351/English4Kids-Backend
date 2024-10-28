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
public class QuestionDTO {
    private Long id;
    private String questionType;
    private String content;
    private String audioUrl;
    private List<AnswerDTO> answers;
}
