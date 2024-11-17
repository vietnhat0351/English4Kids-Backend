package com.example.English4Kids_Backend.dtos.lessonDTO;


import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.enums.VocabularyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VocabularyDTO {
    private long id;
    private String word;
    private String meaning;
    private String pronunciation;
    private VocabularyType type;
    private String image;
    private String audio;

    public VocabularyDTO(Vocabulary vocabulary) {
        this.id = vocabulary.getId();
        this.word = vocabulary.getWord();
        this.meaning = vocabulary.getMeaning();
        this.pronunciation = vocabulary.getPronunciation();
        this.type = vocabulary.getType();
        this.image = vocabulary.getImage();
        this.audio = vocabulary.getAudio();
    }
}
