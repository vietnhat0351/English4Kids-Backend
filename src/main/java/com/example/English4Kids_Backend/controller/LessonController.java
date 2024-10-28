package com.example.English4Kids_Backend.controller;


import com.example.English4Kids_Backend.dtos.LessonDTO;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.mappers.LessonMapper;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
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
    private final VocabularyRepository vocabularyRepository;
    private final LessonMapper lessonMapper;

    @GetMapping("/all-lessons")
    public ResponseEntity<List<LessonDTO>> getAllLessons(){
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonDTO> lessonDTOS = lessons.stream()
                .map(lessonMapper::mapToLessonDTO)
                .toList();
        return ResponseEntity.ok(lessonDTOS);
    }
    @PostMapping("/create-lesson")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson  lesson){
        return ResponseEntity.ok(lessonService.createLesson(lesson));

    }
    @GetMapping ("/find-lesson/{id}")
    public ResponseEntity<LessonDTO> getLesson(@PathVariable Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow();

        // Map the entity to DTO
        LessonDTO lessonDTO = lessonMapper.mapToLessonDTO(lesson);

        return ResponseEntity.ok(lessonDTO);
    }
    @PostMapping("/update-lesson/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lessonDetails) {
        Lesson updatedLesson = lessonService.updateLesson(id, lessonDetails);
        return ResponseEntity.ok(updatedLesson);
    }
    @GetMapping("/find-word/{word}")
    public ResponseEntity<Vocabulary> getVocabulary(@PathVariable String word) {
        Vocabulary v = vocabularyRepository.findByWord(word);
        if (v != null) {
            return ResponseEntity.ok(v);
        }
        else{
            Optional<Vocabulary> vocabulary = vocabularyService.fetchVocabularyFromApi(word);
            if (vocabulary.isPresent()) {
                return ResponseEntity.ok(vocabularyService.createVocabulary(vocabulary.get()));
            }
        }
        return ResponseEntity.notFound().build();
    }


}
