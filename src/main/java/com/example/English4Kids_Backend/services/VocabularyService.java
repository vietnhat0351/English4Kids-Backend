package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.TopicRespository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final TopicRespository topicRespository;

    public List<Vocabulary> getAllVocabularies() {
        return vocabularyRepository.findAll();
    }
    public List<Topic> getAllTopics() {
        return topicRespository.findAll();
    }
    public List<Vocabulary> getVocabulariesByTopic(Long topicId) {
        return vocabularyRepository.findByTopicId(topicId);
    }
}
