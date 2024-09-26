package com.example.English4Kids_Backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VocabularyTopic {
    @Id
    private long id;
    private String name;
    @OneToMany
    private List<Vocabulary> vocabularies;
    private String image;

}
