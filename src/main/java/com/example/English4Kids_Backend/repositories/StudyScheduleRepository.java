package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    Optional<StudySchedule> findFirstByStartTime(LocalDateTime startTime);

    List<StudySchedule> findAllByStartTimeBefore(LocalDateTime now);
}
