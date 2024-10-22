package com.example.English4Kids_Backend.entities;

import com.example.English4Kids_Backend.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // translation, fill_in_blank, listening, story

    @Lob
    private String content; // The question text

    private String audioUrl; // For listening questions (if needed)

    @ManyToOne
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    @ManyToOne
    @JoinColumn(name = "part_id")
    @JsonIgnore
    private LessonPart lessonPart;
}
