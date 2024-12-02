package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {

    @Query("SELECT ul.lesson.id FROM UserLesson ul WHERE ul.user.id = :userId")
    List<Long> findLessonIdsByUserId(Long userId);



    @Query("SELECT ul FROM UserLesson ul WHERE ul.user.id = :userId AND ul.lesson.id = :lessonId")
    List<UserLesson> findByUserIdAndLessonId(@Param("userId") Integer userId, @Param("lessonId") Long lessonId);

    @Query("SELECT COUNT(ul) FROM UserLesson ul WHERE ul.user.id = :userId")
    int countLessonsByUserId(@Param("userId") Integer userId);
}