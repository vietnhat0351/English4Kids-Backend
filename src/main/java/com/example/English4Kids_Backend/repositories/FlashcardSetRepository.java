package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, Long> {
    List<FlashcardSet> findAllByUserId(long userId);

    int deleteByIdIn(List<Long> ids);

}
