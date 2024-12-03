package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyDelete;
import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyInput;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import com.example.English4Kids_Backend.services.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vocabulary")
public class VocabularyController {
    private final VocabularyService vocabularyService;
    private final VocabularyRepository vocabularyRepository;

    @PostMapping("/create-vocabulary")
    public ResponseEntity<VocabularyDTO> createVocabulary(@RequestBody Vocabulary vocabulary) {
        Vocabulary savedVocabulary = vocabularyService.createVocabulary(vocabulary);
        return ResponseEntity.ok(new VocabularyDTO(savedVocabulary));
    }
    @PostMapping("/update-vocabulary")
    public ResponseEntity<VocabularyDTO> updateVocabulary(@RequestBody Vocabulary vocabulary) {
        Vocabulary updatedVocabulary = vocabularyService.updateVocabulary(vocabulary);
        return ResponseEntity.ok(new VocabularyDTO(updatedVocabulary));
    }

    @GetMapping("/vocabularies")
    public ResponseEntity<?> getVocabularies() {
        return ResponseEntity.ok(vocabularyService.getAllVocabularies());
    }

    @GetMapping("/find-word/{word}")
    public ResponseEntity<VocabularyInput> getVocabulary(@PathVariable String word) {
        Vocabulary v = vocabularyRepository.findByWord(word);
        if (v != null) {
            VocabularyInput vocabularyInput = VocabularyInput.builder()
                    .id(v.getId())
                    .word(v.getWord())
                    .meaning(v.getMeaning())
                    .pronunciation(v.getPronunciation())
                    .type(v.getType())
                    .image(v.getImage())
                    .audio(v.getAudio())
                    .inDatabase(true)
                    .build();
            return ResponseEntity.ok(vocabularyInput);
        } else {
            try {
                Optional<Vocabulary> vocabulary = vocabularyService.fetchVocabularyFromApi(word);
                if (vocabulary.isPresent()) {
                    VocabularyInput vocabularyInput = VocabularyInput.builder()
                            .id(vocabulary.get().getId())
                            .word(vocabulary.get().getWord())
                            .meaning(vocabulary.get().getMeaning())
                            .pronunciation(vocabulary.get().getPronunciation())
                            .type(vocabulary.get().getType())
                            .image(vocabulary.get().getImage())
                            .audio(vocabulary.get().getAudio())
                            .inDatabase(false)
                            .build();
                    return ResponseEntity.ok(vocabularyInput);

                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }


        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find-word-fast/{word}")
    public ResponseEntity<VocabularyInput> getVocabularyFast(@PathVariable String word) {
        Vocabulary v = vocabularyRepository.findByWord(word);
        VocabularyInput vocabularyInput;
        if (v != null) {
            vocabularyInput = VocabularyInput.builder()
                    .id(v.getId())
                    .word(v.getWord())
                    .meaning(v.getMeaning())
                    .pronunciation(v.getPronunciation())
                    .type(v.getType())
                    .image(v.getImage())
                    .audio(v.getAudio())
                    .inDatabase(true)
                    .build();
        } else {
            // Trả về đối tượng với inDatabase = false nếu từ không tồn tại
            vocabularyInput = VocabularyInput.builder()
                    .word(word)
                    .inDatabase(false)
                    .build();
        }
        return ResponseEntity.ok(vocabularyInput);
    }
    @PostMapping("/delete-vocabulary")
    public ResponseEntity<?> deleteVocabulary(@RequestBody VocabularyDelete vocabulary) {
        vocabularyService.deleteVoca(vocabulary);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
