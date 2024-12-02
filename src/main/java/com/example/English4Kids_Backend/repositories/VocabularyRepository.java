package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {


    @Query("SELECT v FROM Vocabulary v WHERE v.word = ?1")
    Vocabulary findByWord(String word);

    @Modifying
    @Query(value = "DELETE FROM vocabularies WHERE id = :vocabularyId;", nativeQuery = true)
    void deleteVocaById(@Param("vocabularyId") Long vocabularyId);


}
