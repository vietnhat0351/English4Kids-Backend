package com.example.English4Kids_Backend.entities;

import com.example.English4Kids_Backend.services.SpacedRepetitionService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_vocabulary",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vocabulary_id"}))
public class UserVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    private LocalDate firstAccessed; // Ngày đầu tiên người dùng học từ vựng này
    private LocalDate lastAccessed;  // Ngày cuối cùng người dùng học từ vựng này
    private LocalDate needReview;    // Ngày cần ôn lại từ vựng này
    private Integer count;          // Số lần người dùng học từ vựng này

    public void updateReviewDate() {
        SpacedRepetitionService repetitionService = new SpacedRepetitionService();
        this.needReview = repetitionService.calculateNextReviewDate(this.count, this.lastAccessed);
    }
}