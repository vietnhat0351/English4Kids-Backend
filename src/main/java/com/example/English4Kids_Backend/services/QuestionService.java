package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionDeleteInput;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestAddDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.QuestionRequestDTO;
import com.example.English4Kids_Backend.entities.Answer;
import com.example.English4Kids_Backend.entities.Lesson;
import com.example.English4Kids_Backend.entities.Question;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.enums.QuestionType;
import com.example.English4Kids_Backend.repositories.*;
import jakarta.transaction.Transactional;
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
    private final LessonVocabularyRepository lessonVocabularyRepository;

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
        lesson.getVocabularies().add(new Vocabulary(vocabulary.getId()));
        lessonRepository.save(lesson);
        // Gán answers cho question và trả về kết quả
        savedQuestion.setAnswers(answers);
        return savedQuestion;
    }

    public QuestionDTO updateQuestion(QuestionRequestAddDTO questionRequestDTO) {
        // Tìm kiếm lesson và vocabulary theo ID
//        Lesson lesson = lessonRepository.findById(questionRequestDTO.getLesson().getId())
//                .orElseThrow(() -> new RuntimeException("Lesson not found"));
//
//        Vocabulary vocabulary = vocabularyRepository.findById(questionRequestDTO.getVocabulary().getId())
//                .orElseThrow(() -> new RuntimeException("Vocabulary not found"));

        // Tìm question hiện tại
        Question question = questionRepository.findById(questionRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Cập nhật thông tin question

        question.setContent(questionRequestDTO.getContent());
        question.setType(QuestionType.valueOf(questionRequestDTO.getType()));
        question.setImage(questionRequestDTO.getImage());
        question.setAudio(questionRequestDTO.getAudio());
//        question.setLesson(lesson);
        question.setVocabulary(new Vocabulary(questionRequestDTO.getVocabulary().getId()));

        // Xóa các câu trả lời cũ (nếu cần)
        question.getAnswers().clear();

        // Thêm các câu trả lời mới
        List<Answer> answers = questionRequestDTO.getAnswers().stream().map(answerRequestDTO ->
                Answer.builder()
                        .content(answerRequestDTO.getContent())
                        .image(answerRequestDTO.getImage())
                        .audio(answerRequestDTO.getAudio())
                        .isCorrect(answerRequestDTO.getIsCorrect())
                        .question(question)
                        .build()
        ).toList();

        question.getAnswers().addAll(answers);

        // Lưu lại question và các câu trả lời
        return new QuestionDTO(questionRepository.save(question));
    }

    @Transactional
    public void deleteQuestion(QuestionDeleteInput questionDeleteInput) {
        lessonVocabularyRepository.deleteFirstByLessonIdAndVocabularyId(questionDeleteInput.getLessonId(), questionDeleteInput.getVocabularyId());
        answerRepository.deleteByQuestionId(questionDeleteInput.getQuestionId());
        questionRepository.deleteByQuestionId(questionDeleteInput.getQuestionId());
    }
}
