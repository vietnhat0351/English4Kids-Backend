package com.example.English4Kids_Backend.dtos.lessonDTO;


import com.example.English4Kids_Backend.entities.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {
    private Integer id;
    private String content;
    private String image;
    private String audio;
    private boolean isCorrect;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.image = answer.getImage();
        this.audio = answer.getAudio();
        this.isCorrect = answer.getIsCorrect();
    }
}
