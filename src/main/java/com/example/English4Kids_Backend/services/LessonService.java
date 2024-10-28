package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.repositories.LessonPartRepository;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonPartRepository lessonPartRepository;

    public Lesson createLesson(Lesson lesson){
        if (lesson.getDescription() == null || lesson.getDescription().equalsIgnoreCase("")){
            lesson.setDescription("This is a lesson");
        }


        try {
            if ((lesson.getLessonParts() == null || lesson.getLessonParts().isEmpty())) {

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
            }
            lessonRepository.save(lesson);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return lesson;


    }
    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            Lesson lessonToUpdate = optionalLesson.get();
            lessonToUpdate.setTitle(lessonDetails.getTitle());
            lessonToUpdate.setDescription(lessonDetails.getDescription());
            lessonToUpdate.setLessonParts(lessonDetails.getLessonParts()); // Cập nhật lessonParts
            return lessonRepository.save(lessonToUpdate);
        } else {
            throw new RuntimeException("Lesson not found with id " + id);
        }
    }



}
