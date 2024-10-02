package com.example.English4Kids_Backend.dtos;

import com.example.English4Kids_Backend.entities.Flashcard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlashcardSetRequest {
    private String name;
    private String description;
    private List<FlashcardDTO> flashcards;
}
