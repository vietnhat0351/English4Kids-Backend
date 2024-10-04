package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRespository extends JpaRepository<Topic, Long> {
}
