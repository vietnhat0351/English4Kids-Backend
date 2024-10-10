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
@Builder
public class Vocabulary {
    @Id
    private long id;
    private String word;
    private String meaning;
    private String pronunciation;
    private String image;
    private String audio;
//    private String example;
//    private String exampleMeaning;
    @Enumerated(EnumType.STRING)
    private VocabularyType type;
    @Enumerated(EnumType.STRING)
    private VocabularyLevel level;
//    private String category;
//    private String description;
    @ManyToOne
    @JoinColumn(name = "topicId")

    private Topic topic;

}
