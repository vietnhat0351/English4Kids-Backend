package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.UserVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, Long> {

    Optional<UserVocabulary> findByUserIdAndVocabularyId(Integer userId, Long vocabularyId);

    @Query("SELECT COUNT(uv) FROM UserVocabulary uv WHERE uv.user.id = :userId")
    Long countByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(uv) FROM UserVocabulary uv WHERE uv.user.id = :userId")
    int countVocabulariesByUserId(@Param("userId") Integer userId);

    // Xóa từ vựng theo id

    @Modifying
    @Query(value = "\tDELETE FROM public.user_vocabulary\n" +
            "WHERE vocabulary_id = :vocabularyId;", nativeQuery = true)
    void deleteById(@Param("vocabularyId") Long vocabularyId);
}
