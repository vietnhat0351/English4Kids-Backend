package com.example.English4Kids_Backend.dtos.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProcess {
    private Long id;
    private String title;
    private List<PartProcess> parts;

}
