package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestDTO;
import com.example.English4Kids_Backend.entities.Answer;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.enums.QuestionType;
import com.example.English4Kids_Backend.repositories.AnswerRepository;
import com.example.English4Kids_Backend.repositories.LessonRepository;
import com.example.English4Kids_Backend.repositories.QuestionRepository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final LessonRepository lessonRepository;
    private final VocabularyRepository vocabularyRepository;

    public Question createQuestion(QuestionRequestDTO questionRequestDTO) {
        // Tìm kiếm lesson và vocabulary theo ID
        Lesson lesson = lessonRepository.findById(questionRequestDTO.getLesson().getId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Vocabulary vocabulary = vocabularyRepository.findById(questionRequestDTO.getVocabulary().getId())
                .orElseThrow(() -> new RuntimeException("Vocabulary not found"));

        // Khởi tạo đối tượng Question
        Question question = Question.builder()
                .content(questionRequestDTO.getContent())
                .type(QuestionType.valueOf(questionRequestDTO.getType()))
                .image(questionRequestDTO.getImage())
                .audio(questionRequestDTO.getAudio())
                .lesson(lesson)
                .vocabulary(vocabulary)
                .build();

        // Lưu question trước để có ID cần thiết
        Question savedQuestion = questionRepository.save(question);

        // Tạo các Answer từ danh sách câu trả lời và gán cho question
        List<Answer> answers = questionRequestDTO.getAnswers().stream().map(answerRequestDTO ->
                Answer.builder()
                        .content(answerRequestDTO.getContent())
                        .image(answerRequestDTO.getImage())
                        .audio(answerRequestDTO.getAudio())
                        .isCorrect(answerRequestDTO.getIsCorrect())
                        .question(savedQuestion)
                        .build()
        ).collect(Collectors.toList());

        // Lưu danh sách Answer
        answerRepository.saveAll(answers);
        lesson.getVocabularies().add(new Vocabulary(vocabulary.getId() ));
        lessonRepository.save(lesson);
        // Gán answers cho question và trả về kết quả
        savedQuestion.setAnswers(answers);
        return savedQuestion;
    }
}
