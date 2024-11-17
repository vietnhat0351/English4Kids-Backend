package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.entities.UserVocabulary;
import com.example.English4Kids_Backend.repositories.UserVocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserVocabularyService {
    private final UserVocabularyRepository userVocabularyRepository;

    public void userStudiedVocabulary(UserVocabulary userVocabulary) {
        userVocabulary.setLastAccessed(LocalDate.now());
        userVocabulary.setCount(userVocabulary.getCount() + 1);
        userVocabulary.updateReviewDate();
        userVocabularyRepository.save(userVocabulary);
    }


}
