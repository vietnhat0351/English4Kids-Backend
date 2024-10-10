package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.dtos.TopicDTO;
import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.services.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vocabulary")
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping("/all-topics")
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        return ResponseEntity.ok(vocabularyService.getAllTopics());
    }
    @GetMapping("/get-vocabularies-by-topic")
    public ResponseEntity<List<Vocabulary>> getVocabulariesByTopic(@RequestParam Long topicId) {
        System.out.println("topicId = " + topicId);
        return ResponseEntity.ok(vocabularyService.getVocabulariesByTopic(topicId));
    }
    @PostMapping("/create-topic")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        return ResponseEntity.ok(vocabularyService.createTopic(topic));
    }
    @PostMapping("/delete-topic")
    public ResponseEntity<String> deleteTopic(@RequestParam Long topicId) {
        return ResponseEntity.ok(vocabularyService.deleteTopic(topicId));
    }
    @PostMapping("/update-topic")
    public ResponseEntity<Topic> updateTopic(@RequestBody Topic topic) {
        return ResponseEntity.ok(vocabularyService.createTopic(topic));
    }
    @PostMapping("/create-vocabulary")
    public ResponseEntity<Vocabulary> createVocabulary(@RequestBody Vocabulary vocabulary) {
        return ResponseEntity.ok(vocabularyService.createVocabulary(vocabulary));
    }

}
