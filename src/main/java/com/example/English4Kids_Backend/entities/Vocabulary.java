package com.example.English4Kids_Backend.entities;

import com.example.English4Kids_Backend.enums.VocabularyLevel;
import com.example.English4Kids_Backend.enums.VocabularyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    private VocabularyType type;
    private VocabularyLevel level;
//    private String category;
//    private String description;
    @ManyToOne
    @JoinColumn(name = "topicId")
    @JsonIgnore
    private Topic topic;

}
