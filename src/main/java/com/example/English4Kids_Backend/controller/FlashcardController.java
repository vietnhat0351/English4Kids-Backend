package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.CreateFlashcardSetRequest;
import com.example.English4Kids_Backend.dtos.DeleteFlashcardSetByIdInReq;
import com.example.English4Kids_Backend.dtos.UpdateCardMatchingRecordRequest;
import com.example.English4Kids_Backend.dtos.UserCardMatchingRecords;
import com.example.English4Kids_Backend.entities.CardMatchingRecord;
import com.example.English4Kids_Backend.entities.FlashcardSet;
import com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser.*;
import com.example.English4Kids_Backend.services.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    @PostMapping("/create-flashcard-set")
    public FlashcardSet createFlashcardSet(@RequestBody CreateFlashcardSetRequest request) {
        return flashcardService.createFlashcardSet(request);
    }

    @GetMapping("/get-all-flashcard-sets")
    public List<FlashcardSetDTO> getAllFlashcardSetsByUser() {
        return flashcardService.getAllFlashcardSetsByUser();
    }

    @GetMapping("/get-flashcard-set/{id}")
    public FlashcardSetDTO getFlashcardSetById(@PathVariable long id) {
        return flashcardService.getFlashcardSetById(id);
    }

    @PostMapping("/delete-flashcard-sets")
    public ResponseEntity<String> deleteFlashcardSetByIdIn(@RequestBody DeleteFlashcardSetByIdInReq request) {
        return ResponseEntity.ok(flashcardService.deleteFlashcardSetByIdIn(request.getIds()));
    }

    @PostMapping("/update-flashcard-set/{id}")
    public FlashcardSet updateFlashcardSet(@PathVariable long id, @RequestBody CreateFlashcardSetRequest request) {
        return flashcardService.updateFlashcardSet(id, request);
    }

    @PostMapping("/update-card-matching-record/{flashcardSetID}")
    public FlashcardSet updateCardMatchingRecord(@PathVariable long flashcardSetID, @RequestBody UpdateCardMatchingRecordRequest data) {
        return flashcardService.updateCardMatchingRecord(flashcardSetID, data);
    }

    @GetMapping("/get-user-card-matching-record/{userID}")
    public List<UserCardMatchingRecords> getUserCardMatchingRecord(@PathVariable long userID) {
        return flashcardService.getUserCardMatchingRecord(userID);
    }
}
