package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.dtos.TopicDTO;
import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.TopicRespository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final TopicRespository topicRespository;

    public List<Vocabulary> getAllVocabularies() {
        return vocabularyRepository.findAll();
    }
    public List<TopicDTO> getAllTopics() {
        return topicRespository.findAllTopic();
    }
    public List<Vocabulary> getVocabulariesByTopic(Long topicId) {
        return vocabularyRepository.findByTopicId(topicId);
    }
    public Topic createTopic(Topic topic) {
        if (topic.getImage() == null || topic.getImage().equalsIgnoreCase("")){
            topic.setImage("https://english-for-kids.s3.ap-southeast-1.amazonaws.com/trends.gif");
        }
        topic.setVocabularies(new ArrayList<>());
        return topicRespository.save(topic);
    }
    public String deleteTopic(Long topicId) {
        topicRespository.deleteById(topicId);
        return "Deleted";
    }
    public Vocabulary createVocabulary(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }
}
