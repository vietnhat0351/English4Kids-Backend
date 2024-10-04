package com.example.English4Kids_Backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardDTO {
    private long id;
    private String word;
    private String meaning;
    private String image;
    private String audio;
    private String category;
    private String description;
}
