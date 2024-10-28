package com.example.English4Kids_Backend.dtos.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartProcess {
    private Long id;
    private Integer partNumber;
    private Integer time;
}
