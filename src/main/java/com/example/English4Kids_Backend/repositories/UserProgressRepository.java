package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.entities.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

}
