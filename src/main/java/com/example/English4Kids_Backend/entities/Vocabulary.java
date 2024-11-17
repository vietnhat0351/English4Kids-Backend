package com.example.English4Kids_Backend.entities;

import com.example.English4Kids_Backend.enums.VocabularyType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vocabularies")
@Builder
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String word;
    private String meaning;

    private String pronunciation;
    @Enumerated(EnumType.STRING)
    private VocabularyType type;

    private String image;
    private String audio;

    @ManyToMany(mappedBy = "vocabularies")
    private List<Lesson> lessons = new ArrayList<>();

    public Vocabulary(long id) {
        this.id = id;
    }
}
