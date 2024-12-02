package com.example.English4Kids_Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String image;
    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLesson> userLessons = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "lesson_vocabulary",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "vocabulary_id")
//            uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id", "vocabulary_id"})
    )
    private List<Vocabulary> vocabularies = new ArrayList<>();

    public Lesson(long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }
}
