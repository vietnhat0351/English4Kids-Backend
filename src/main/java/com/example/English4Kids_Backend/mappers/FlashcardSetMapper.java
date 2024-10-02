package com.example.English4Kids_Backend.mappers;

import com.example.English4Kids_Backend.dtos.CreateFlashcardSetRequest;
import com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser.FlashcardSetDTO;
import com.example.English4Kids_Backend.entities.FlashcardSet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FlashcardMapper.class})
public interface FlashcardSetMapper {
    FlashcardSet createFlashcardSetRequestToFlashcardSet(CreateFlashcardSetRequest request);
    FlashcardSetDTO flashcardSetToFlashcardSetResponse(FlashcardSet flashcardSet);
}
