package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    List<UserSession> findByUserAndLessonPart(User user, LessonPart lessonPart);

    @Query("SELECT COUNT(us) FROM UserSession us WHERE us.user = :user AND us.lessonPart = :lessonPart")
    Integer countUserSessionsByUserAndLessonPart(@Param("user") User user, @Param("lessonPart") LessonPart lessonPart);
}
