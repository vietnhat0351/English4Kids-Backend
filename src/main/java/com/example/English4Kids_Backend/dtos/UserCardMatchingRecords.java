package com.example.English4Kids_Backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCardMatchingRecords {
    private String id;
    private long flashcardSetId;
    private String flashcardSetName;
    private long timeRecord;
    private int playCount;
    private int pairCount;
}
