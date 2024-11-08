package com.example.English4Kids_Backend.controller;

import com.example.English4Kids_Backend.entities.Answer;
import com.example.English4Kids_Backend.entities.LessonPart;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PostMapping("/create-questions")
    public ResponseEntity<List<Question>> createQuestions(@RequestBody List<Question> questions) {
        for (Question question : questions) {
            if (question.getAnswers() != null) {
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestion(question);
                }
            }
            System.out.println(question.getLessonPart().getId());
        }

        List<Question> savedQuestions = questionRepository.saveAll(questions);

        return ResponseEntity.ok(savedQuestions);
    }
    @PostMapping("/delete-question/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        if (questionRepository.existsById(id)) {
            try {
                questionRepository.deleteById(id);
                return ResponseEntity.ok("Deleted question with id " + id);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting question with id " + id);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with id " + id + " not found");
        }
    }
    @GetMapping("/get-question/{id}")
    public ResponseEntity<Question> getQuestionById( @PathVariable String id) {
        try {
            Optional<Question> question = questionRepository.findById(Long.valueOf(id));
            if (question.isPresent()) {
                Question questionCurrent = question.get();
                questionCurrent.setLessonPart(new LessonPart(questionCurrent.getLessonPart().getId()));
                questionCurrent.setVocabulary(new Vocabulary(questionCurrent.getVocabulary().getId()));

                return ResponseEntity.ok(questionCurrent);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
