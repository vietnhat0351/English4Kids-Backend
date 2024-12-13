package com.example.English4Kids_Backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "wordshake_high_score")
@AllArgsConstructor
@NoArgsConstructor
public class WordshakeHighScore {
    @Id
    private Integer id;
    private Integer highScore;
    private LocalDateTime createdDate;
}
