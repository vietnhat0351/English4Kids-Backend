package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.repositories.LessonPartRepository;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonPartRepository lessonPartRepository;

    public Lesson createLesson(Lesson lesson){

        try {
            LessonPart lessonPart1 = LessonPart.builder()
                    .partNumber(1)
                    .lesson(lesson)
                    .stories(new ArrayList<>())
                    .questions(new ArrayList<>())
                    .build();
            LessonPart lessonPart2 = LessonPart.builder()
                    .partNumber(2)
                    .lesson(lesson)
                    .stories(new ArrayList<>())
                    .questions(new ArrayList<>())
                    .build();
            LessonPart lessonPart3 = LessonPart.builder()
                    .partNumber(3)
                    .lesson(lesson)
                    .stories(new ArrayList<>())
                    .questions(new ArrayList<>())
                    .build();

            List<LessonPart> lessonParts = new ArrayList<>();
            lessonParts.add(lessonPart1);
            lessonParts.add(lessonPart2);
            lessonParts.add(lessonPart3);

            lesson.setLessonParts(lessonParts);
            lessonRepository.save(lesson);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return lesson;

    }


}
