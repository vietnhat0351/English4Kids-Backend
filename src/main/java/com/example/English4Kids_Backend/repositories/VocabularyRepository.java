package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    @Query("SELECT v FROM Vocabulary v WHERE v.topic.topicId = ?1")
    List<Vocabulary> findByTopicId(Long topicId);


}
