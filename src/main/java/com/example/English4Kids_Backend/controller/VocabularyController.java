package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.services.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vocabulary")
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping("/all-topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(vocabularyService.getAllTopics());
    }
    @GetMapping("/get-vocabularies-by-topic")
    public ResponseEntity<List<Vocabulary>> getVocabulariesByTopic(@RequestParam Long topicId) {
        System.out.println("topicId = " + topicId);
        return ResponseEntity.ok(vocabularyService.getVocabulariesByTopic(topicId));
    }

}
