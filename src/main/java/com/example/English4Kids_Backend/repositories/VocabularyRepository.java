package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {


    @Query("SELECT v FROM Vocabulary v WHERE v.word = ?1")
    Vocabulary findByWord(String word);




}
