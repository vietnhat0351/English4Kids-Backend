package com.example.English4Kids_Backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCardMatchingRecordRequest {
    private String id;
    private long userId;
    private long timeRecord;
    private int playCount;
}
