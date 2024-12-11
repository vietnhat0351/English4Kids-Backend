package com.example.English4Kids_Backend.repositories;

import com.example.English4Kids_Backend.dtos.lessonDTO.StatisticDTO;
import com.example.English4Kids_Backend.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository  extends JpaRepository<Lesson, Long> {

    @Query(value = "WITH date_range AS (\n" +
            "    SELECT \n" +
            "        generate_series(\n" +
            "            CURRENT_DATE - INTERVAL '6 days', \n" +
            "            CURRENT_DATE, \n" +
            "            '1 day'\n" +
            "        )::DATE AS day\n" +
            "),\n" +
            "user_scores AS (\n" +
            "    SELECT \n" +
            "        user_id,\n" +
            "        DATE(date) AS day,\n" +
            "        SUM(score) AS total_score\n" +
            "    FROM \n" +
            "        public.user_lesson\n" +
            "    WHERE \n" +
            "        date >= CURRENT_DATE - INTERVAL '7 days'\n" +
            "    GROUP BY \n" +
            "        user_id, DATE(date)\n" +
            ")\n" +
            "SELECT \n" +
            "    u.user_id,\n" +
            "    d.day,\n" +
            "    COALESCE(us.total_score, 0) AS total_score\n" +
            "FROM \n" +
            "    (SELECT DISTINCT user_id FROM public.user_lesson) u\n" +
            "CROSS JOIN \n" +
            "    date_range d\n" +
            "LEFT JOIN \n" +
            "    user_scores us\n" +
            "ON \n" +
            "    u.user_id = us.user_id AND d.day = us.day\n" +
            "WHERE u.user_id = :userId\n" +
            "ORDER BY \n" +
            "    u.user_id, d.day;", nativeQuery = true)
    List<Object[]> getPointSevenDay(@Param("userId") int userId);

    @Query(value = "SELECT \n" +
            "    ROW_NUMBER() OVER (ORDER BY date DESC, id DESC) AS attempt_rank,\n" +
            "    id, \n" +
            "    score, \n" +
            "    \"time\", \n" +
            "    type, \n" +
            "    lesson_id, \n" +
            "    user_id\n" +
            "FROM \n" +
            "    user_lesson\n" +
            "WHERE \n" +
            "    lesson_id = :lessonId \n" +
            "\tAND user_id = :userId\n" +
            "    AND type = 'QUIZ'\n" +
            "ORDER BY \n" +
            "    date DESC, \n" +
            "    id DESC\n" +
            "LIMIT 7;\n", nativeQuery = true)
    List<Object[]> getStatistic(@Param("lessonId") Long lessonId, @Param("userId") Long userId);
}
