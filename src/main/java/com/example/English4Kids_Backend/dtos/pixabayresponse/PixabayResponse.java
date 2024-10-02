package com.example.English4Kids_Backend.dtos.pixabayresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PixabayResponse {
    private long total;
    private int totalHits;
    private List<ImageResult> hits;
}
