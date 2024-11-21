package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.UserVocabulary;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.UserVocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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
    public void userStudiedVocabulary(Integer userId, Long vocabularyId) {
        Optional<UserVocabulary> existing = userVocabularyRepository.findByUserIdAndVocabularyId(userId, vocabularyId);

        if (existing.isPresent()) {

            UserVocabulary userVocabulary = existing.get();


            if (userVocabulary.getNeedReview().plusDays(7).isBefore(LocalDate.now()) || userVocabulary.getNeedReview().equals(LocalDate.now())) {
                userVocabulary.setCount(userVocabulary.getCount() + 1);
            }

            userVocabulary.setLastAccessed(LocalDate.now());
            userVocabulary.updateReviewDate();
            userVocabularyRepository.save(userVocabulary);
        } else {
            // Nếu chưa tồn tại, tạo mới
            UserVocabulary newUserVocabulary = UserVocabulary.builder()
                    .user(User.builder().id(userId).build())
                    .vocabulary(Vocabulary.builder().id(vocabularyId).build())
                    .firstAccessed(LocalDate.now())
                    .lastAccessed(LocalDate.now())
                    .needReview(LocalDate.now().plusDays(1)) // Ví dụ, ngày ôn lại đầu tiên là ngày tiếp theo
                    .count(1)
                    .build();
            userVocabularyRepository.save(newUserVocabulary);
        }


    }
    public Long countVocabulariesByUserId(Integer userId) {
        return userVocabularyRepository.countByUserId(userId);
    }


}
