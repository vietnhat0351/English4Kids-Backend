package com.example.English4Kids_Backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flashcard {
    @Id
    private String word;
    private String meaning;
    private String image;
    private String audio;
    private String category;
    private String description;
}
