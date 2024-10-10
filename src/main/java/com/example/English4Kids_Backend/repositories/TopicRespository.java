package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.dtos.TopicDTO;
import com.example.English4Kids_Backend.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRespository extends JpaRepository<Topic, Long> {

    @Query("SELECT new com.example.English4Kids_Backend.dtos.TopicDTO(t.topicId, t.name, t.image, SIZE(t.vocabularies) ) FROM Topic t")
    List<TopicDTO> findAllTopic();

}
