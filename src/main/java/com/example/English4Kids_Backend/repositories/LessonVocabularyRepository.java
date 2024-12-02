package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.LessonVocabulary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonVocabularyRepository extends JpaRepository<LessonVocabulary, Long> {


//    @Modifying
//    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.lesson_vocabulary\n" +
            "WHERE id = (\n" +
            "    SELECT id\n" +
            "    FROM public.lesson_vocabulary\n" +
            "    WHERE lesson_id = :lessonId AND vocabulary_id = :vocabularyId\n" +
            "    ORDER BY id\n" +
            "    LIMIT 1\n" +
            ");", nativeQuery = true)
    void deleteFirstByLessonIdAndVocabularyId(@Param("lessonId") Long lessonId, @Param("vocabularyId") Long vocabularyId);

    @Modifying
    @Query(value = "DELETE FROM lesson_vocabulary\n" +
            "WHERE vocabulary_id = :vocabularyId;", nativeQuery = true)
    void deleteByVocabularyId( @Param("vocabularyId") Long vocabularyId);


}
