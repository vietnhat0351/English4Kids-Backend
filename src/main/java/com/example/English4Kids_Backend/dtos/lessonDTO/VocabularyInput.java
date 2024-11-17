package com.example.English4Kids_Backend.dtos.lessonDTO;
import com.example.English4Kids_Backend.enums.VocabularyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VocabularyInput {
    private long id;
    private String word;
    private String meaning;
    private String pronunciation;
    private VocabularyType type;
    private String image;
    private String audio;
    private boolean inDatabase;

}
