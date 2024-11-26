package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.CreateFlashcardSetRequest;
import com.example.English4Kids_Backend.dtos.UpdateCardMatchingRecordRequest;
import com.example.English4Kids_Backend.dtos.UserCardMatchingRecords;
import com.example.English4Kids_Backend.dtos.getAllFlashcardSetsByUser.FlashcardSetDTO;
import com.example.English4Kids_Backend.entities.CardMatchingRecord;
import com.example.English4Kids_Backend.entities.Flashcard;
import com.example.English4Kids_Backend.entities.FlashcardSet;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.mappers.FlashcardMapper;
import com.example.English4Kids_Backend.mappers.FlashcardSetMapper;
import com.example.English4Kids_Backend.repositories.ExtendedFlashcardSetRepository;
import com.example.English4Kids_Backend.repositories.FlashcardRepository;
import com.example.English4Kids_Backend.repositories.FlashcardSetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final FlashcardSetRepository flashcardSetRepository;
    private final ExtendedFlashcardSetRepository extendedFlashcardSetRepository;
    private final FlashcardSetMapper flashcardSetMapper;
    private final FlashcardMapper flashcardMapper;

    public FlashcardSet createFlashcardSet(CreateFlashcardSetRequest request) {
        FlashcardSet flashcardSet = flashcardSetMapper.createFlashcardSetRequestToFlashcardSet(request);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        flashcardSet.setUsers(List.of(user));
        flashcardSet.setCreatedAt(LocalDateTime.now());
        flashcardSet.setCardMatchingRecords(new ArrayList<>());
        return flashcardSetRepository.save(flashcardSet);
    }

    public List<FlashcardSetDTO> getAllFlashcardSetsByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<FlashcardSet> flashcardSets = extendedFlashcardSetRepository.findAllUserId(user.getId());
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

    public FlashcardSet updateFlashcardSet(long id, CreateFlashcardSetRequest request) {
        FlashcardSet flashcardSet = flashcardSetRepository.findById(id).orElseThrow();
        flashcardSet.setName(request.getName());
        flashcardSet.setDescription(request.getDescription());
        List<Flashcard> flashcards = request.getFlashcards().stream().map(flashcardDTO -> {
            Flashcard flashcard = flashcardMapper.flashcardDTOToFlashcard(flashcardDTO);
            flashcard.setId(UUID.randomUUID().toString());
            return flashcard;
        }).collect(Collectors.toList());
        flashcardSet.setFlashcards(flashcards);
        System.out.println(flashcardSet.getFlashcards());
        return flashcardSetRepository.save(flashcardSet);
    }

    public FlashcardSet updateCardMatchingRecord(long flashcardSetID, UpdateCardMatchingRecordRequest data) {
        FlashcardSet flashcardSet = flashcardSetRepository.findById(flashcardSetID).orElseThrow();
        boolean isRecordExist = flashcardSet.getCardMatchingRecords().stream()
                .anyMatch(record -> record.getId().equalsIgnoreCase(data.getId()));
        if (isRecordExist) {
            flashcardSet.getCardMatchingRecords().removeIf(record -> record.getId().equalsIgnoreCase(data.getId()));
        }
        flashcardSet.getCardMatchingRecords().add(CardMatchingRecord.builder()
                .id(data.getId())
                .timeRecord(data.getTimeRecord())
                .playCount(data.getPlayCount())
                .userId(data.getUserId())
                .build());
        return flashcardSetRepository.save(flashcardSet);
    }

    public List<UserCardMatchingRecords> getUserCardMatchingRecord(long userID) {
        List<FlashcardSet> flashcardSets = flashcardSetRepository.findAll().stream().filter(flashcardSet -> {
            return flashcardSet.getUsers().stream().anyMatch(user -> user.getId() == userID);
        }).collect(Collectors.toList());

        return flashcardSets.stream().map(flashcardSet -> {

            if (flashcardSet.getCardMatchingRecords() == null) {
                flashcardSet.setCardMatchingRecords(new ArrayList<>());
            }

            CardMatchingRecord cardMatchingRecord = flashcardSet.getCardMatchingRecords().stream()
                    .filter(record -> {
                        return record.getUserId() == userID;
                    })
                    .findFirst()
                    .orElse(CardMatchingRecord.builder()
                            .timeRecord(0)
                            .playCount(0)
                            .id(UUID.randomUUID().toString())
                            .build());

            UserCardMatchingRecords userCardMatchingRecords = UserCardMatchingRecords.builder()
                    .flashcardSetId(flashcardSet.getId())
                    .flashcardSetName(flashcardSet.getName())
                    .timeRecord(cardMatchingRecord.getTimeRecord())
                    .playCount(cardMatchingRecord.getPlayCount())
                    .pairCount(flashcardSet.getFlashcards().size())
                    .id(cardMatchingRecord.getId())
                    .build();
            return userCardMatchingRecords;
        }).toList();
    }
}
