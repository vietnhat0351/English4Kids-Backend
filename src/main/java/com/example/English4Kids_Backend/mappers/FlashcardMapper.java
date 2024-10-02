package com.example.English4Kids_Backend.mappers;

import com.example.English4Kids_Backend.dtos.FlashcardDTO;
import com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser.GAFSBU_Flashcard;
import com.example.English4Kids_Backend.entities.Flashcard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {
    Flashcard flashcardDTOToFlashcard(FlashcardDTO flashcardDTO);
    FlashcardDTO flashcardToFlashcardDTO(Flashcard flashcard);
    GAFSBU_Flashcard flashcardToFlashcard(Flashcard flashcard);
}
