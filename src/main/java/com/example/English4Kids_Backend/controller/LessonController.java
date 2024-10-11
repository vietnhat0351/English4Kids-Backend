package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.services.LessonService;
import com.example.English4Kids_Backend.services.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final LessonRepository lessonRepository;
    private final VocabularyService vocabularyService;

    @GetMapping("/all-lessons")
    public ResponseEntity<List<Lesson>> getAllLessons(){
        return ResponseEntity.ok(lessonRepository.findAll());
    }
    @PostMapping("/create-lesson")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson  lesson){
        return ResponseEntity.ok(lessonService.createLesson(lesson));
    }
//    @GetMapping("/{word}")
//    public ResponseEntity<Vocabulary> getVocabulary(@PathVariable String word) {
//        Vocabulary vocabulary = vocabularyService.getOrCreateVocabulary(word);
//        return ResponseEntity.ok(vocabulary);
//    }
    @GetMapping("/{word}")
    public Vocabulary getVocabulary(@PathVariable String word) {
        Optional<Vocabulary> vocabulary = vocabularyService.fetchVocabularyFromApi(word);
        return vocabulary.orElse(null);
    }

}
