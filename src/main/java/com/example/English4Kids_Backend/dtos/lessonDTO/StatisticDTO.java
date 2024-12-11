package com.example.English4Kids_Backend.dtos.lessonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticDTO {
    private int user_id;
    private String day;
    private int total_score;

    public static StatisticDTO fromObject(Object[] result) {
        // Chuyển đổi từ java.sql.Date thành String (theo định dạng yyyy-MM-dd)
        java.sql.Date date = (java.sql.Date) result[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayString = sdf.format(date);

        Double score = (Double) result[2];  // Lấy giá trị điểm (có thể là Double)
        int totalScore = score.intValue();  // Chuyển thành Integer

        return StatisticDTO.builder()
                .user_id((Integer) result[0])
                .day(dayString)
                .total_score(totalScore)
                .build();
    }
}
