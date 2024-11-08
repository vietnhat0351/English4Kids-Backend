package com.example.English4Kids_Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson_parts")
@Builder
public class LessonPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer partNumber; // 1 for translation, 2 for fill-in-the-blank, etc.

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @JsonIgnore
    private Lesson lesson;

    @OneToMany(mappedBy = "lessonPart")
    private List<Question> questions;

    @OneToMany(mappedBy = "lessonPart")
    private List<Story> stories;

    public LessonPart(Long id) {
        this.id = id;
    }
}
