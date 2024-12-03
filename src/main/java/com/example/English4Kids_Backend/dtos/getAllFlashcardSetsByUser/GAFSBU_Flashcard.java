package com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GAFSBU_Flashcard {
    private String id;
    private String word;
    private String meaning;
    private String image;
    private String description;
    private String phonetic;
}
