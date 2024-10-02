package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.CreateFlashcardSetRequest;
import com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser.FlashcardSetDTO;
import com.example.English4Kids_Backend.entities.FlashcardSet;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.mappers.FlashcardSetMapper;
import com.example.English4Kids_Backend.repositories.FlashcardRepository;
import com.example.English4Kids_Backend.repositories.FlashcardSetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final FlashcardSetRepository flashcardSetRepository;
    private final FlashcardSetMapper flashcardSetMapper;

    public FlashcardSet createFlashcardSet(CreateFlashcardSetRequest request) {
        FlashcardSet flashcardSet = flashcardSetMapper.createFlashcardSetRequestToFlashcardSet(request);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        flashcardSet.setUser(user);
        flashcardSet.setCreatedAt(LocalDateTime.now());
        return flashcardSetRepository.save(flashcardSet);
    }

    public List<FlashcardSetDTO> getAllFlashcardSetsByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<FlashcardSet> flashcardSets = flashcardSetRepository.findAllByUserId(user.getId());
        return flashcardSets.stream().map(flashcardSetMapper::flashcardSetToFlashcardSetResponse).toList();
    }

    public FlashcardSetDTO getFlashcardSetById(long id) {
        FlashcardSet flashcardSet = flashcardSetRepository.findById(id).orElseThrow();
        return flashcardSetMapper.flashcardSetToFlashcardSetResponse(flashcardSet);
    }

    public void deleteFlashcardSet(long id) {
        flashcardSetRepository.deleteById(id);
    }

    @Transactional
    public String deleteFlashcardSetByIdIn(List<Long> ids) {
        int deleted = flashcardSetRepository.deleteByIdIn(ids);
        return "Deleted " + deleted + " flashcard sets";
    }
}
