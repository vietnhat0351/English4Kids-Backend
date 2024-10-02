package com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardSetDTO {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<GAFSBU_Flashcard> flashcards;
}
