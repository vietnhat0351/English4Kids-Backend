package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

}
