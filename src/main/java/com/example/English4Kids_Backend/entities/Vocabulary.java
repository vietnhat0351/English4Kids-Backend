package com.example.English4Kids_Backend.entities;

import com.example.English4Kids_Backend.enums.VocabularyLevel;
import com.example.English4Kids_Backend.enums.VocabularyType;
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
@Table(name = "vocabularies")
@Builder
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String word;
    private String meaning;
    private String vietnameseMeaning;
    private String pronunciation;
//    private String image;
    private String audio;

    @Enumerated(EnumType.STRING)
    private VocabularyType type;

    @Enumerated(EnumType.STRING)
    private VocabularyLevel level;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;

    public Vocabulary(long id) {
        this.id = id;
    }
}
