package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.entities.Answer;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @PostMapping("/create-question")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        if (question.getAnswers() != null) {
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question); // Set question reference for each answer
            }
        }
        System.out.println(question.getLessonPart().getId());

        return ResponseEntity.ok(questionRepository.save(question));


    }
}
