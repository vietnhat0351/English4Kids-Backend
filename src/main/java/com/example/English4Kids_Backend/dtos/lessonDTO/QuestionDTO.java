package com.example.English4Kids_Backend.dtos.lessonDTO;

import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private Integer id;
    private String content;
    private QuestionType type;
    private String image;
    private String audio;
    private List<AnswerDTO> answers;
    private VocabularyDTO vocabulary;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.type = question.getType();
        this.image = question.getImage();
        this.audio = question.getAudio();
        if (question.getVocabulary() != null) {
            this.vocabulary = new VocabularyDTO(question.getVocabulary());
        }
        this.answers = question.getAnswers().stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }
}