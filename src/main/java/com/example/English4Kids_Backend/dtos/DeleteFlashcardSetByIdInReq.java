package com.example.English4Kids_Backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFlashcardSetByIdInReq {
    private List<Long> ids;
}
