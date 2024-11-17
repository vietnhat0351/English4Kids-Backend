package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.UserVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, Long> {
    List<UserVocabulary> findByUserId(Integer userId);
    List<UserVocabulary> findByVocabularyId(Long vocabularyId);
}
