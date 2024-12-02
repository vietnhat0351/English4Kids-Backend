package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Query(value = "DELETE FROM question\n" +
            "WHERE id = :id", nativeQuery = true)
    void deleteByQuestionId(@Param("id") Long id) ;

    @Modifying
    @Query(value = "DELETE FROM public.question\n" +
            "WHERE vocabulary_id = :vocabularyId;", nativeQuery = true)
    void deleteByVocabularyId(@Param("vocabularyId") Long vocabularyId) ;

}
