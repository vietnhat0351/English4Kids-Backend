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
@Table(name = "stories")
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content; // Story content

    @ManyToOne
    @JoinColumn(name = "part_id")
    @JsonIgnore
    private LessonPart lessonPart;

    @ManyToMany
    @JoinTable(
            name = "story_vocabulary",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "vocabulary_id")
    )
    private List<Vocabulary> vocabularies;

}
