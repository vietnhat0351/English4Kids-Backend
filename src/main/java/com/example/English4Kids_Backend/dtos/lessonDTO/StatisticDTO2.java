package com.example.English4Kids_Backend.dtos.lessonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticDTO2 {
    private int attemptRank;
    private int id;
    private int score;
    private int time; // assuming time is in seconds
    private String type;
    private int lessonId;
    private int userId;

    public static StatisticDTO2 fromObject(Object[] result) {
        return StatisticDTO2.builder()
                .attemptRank(((Number) result[0]).intValue()) // attempt_rank
                .id(((Number) result[1]).intValue())         // id
                .score(((Number) result[2]).intValue())      // score
                .time(((Number) result[3]).intValue())       // time
                .type((String) result[4])                   // type
                .lessonId(((Number) result[5]).intValue())   // lesson_id
                .userId(((Number) result[6]).intValue())     // user_id
                .build();
    }
}
