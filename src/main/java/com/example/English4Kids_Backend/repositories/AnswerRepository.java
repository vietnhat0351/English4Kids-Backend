package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.Answer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM answer WHERE question_id = :questionId", nativeQuery = true)
    void deleteByQuestionId(@Param("questionId") Long questionId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.answer\n" +
            "WHERE question_id IN (\n" +
            "    SELECT id FROM public.question\n" +
            "    WHERE vocabulary_id = :vocabularyId\n" +
            ");", nativeQuery = true)
    void deleteByVocabularyId (@Param("vocabularyId") Long vocabularyId);
}
