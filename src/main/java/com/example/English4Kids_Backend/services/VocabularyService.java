package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyDTO;
import com.example.English4Kids_Backend.dtos.lessonDTO.VocabularyDelete;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.enums.VocabularyType;

import com.example.English4Kids_Backend.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final TranslationService translationService;
    private final QuestionRepository questionRepository;
    private final UserVocabularyRepository userVocabularyRepository;
    private final LessonVocabularyRepository lessonVocabularyRepository;
    private final AnswerRepository answerRepository;

    public List<VocabularyDTO> getAllVocabularies() {
        try {
            List<Vocabulary> vocabularies = vocabularyRepository.findAll();
            return vocabularies.stream()
                    .map(VocabularyDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }


    public Vocabulary createVocabulary(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    public Optional<Vocabulary> fetchVocabularyFromApi(String word) {
        // Giả sử đây là nơi bạn gọi API bên thứ 3
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

        RestTemplate restTemplate = new RestTemplate();
        Vocabulary vocabulary = new Vocabulary();

        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(apiUrl, Object[].class);
            Object[] vocabInfo = response.getBody();

            if (vocabInfo != null && vocabInfo.length > 0) {
                // Chuyển đổi vocabInfo[0] thành Map
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> vocabData = objectMapper.convertValue(vocabInfo[0], Map.class);

                // Truy cập các phần tử cụ thể
                String wordReturned = (String) vocabData.get("word");
                vocabulary.setWord(wordReturned);
                System.out.println("Word: " + wordReturned);


                List<Map<String, Object>> phonetics = (List<Map<String, Object>>) vocabData.get("phonetics");
                List<Map<String, Object>> meanings = (List<Map<String, Object>>) vocabData.get("meanings");

                // Duyệt qua danh sách phonetics
                for (Map<String, Object> phonetic : phonetics) {
                    if (phonetic.get("text") != null || !phonetic.get("text").equals(""))
                        vocabulary.setPronunciation((String) phonetic.get("text"));
                    if (phonetic.get("audio") != null || !phonetic.get("audio").toString().equalsIgnoreCase("".trim()))
                        vocabulary.setAudio((String) phonetic.get("audio"));
                    if (vocabulary.getPronunciation() != null && vocabulary.getAudio() != null
                            && !vocabulary.getPronunciation().equalsIgnoreCase("") && !vocabulary.getAudio().equalsIgnoreCase(""))
                        break;
                }

                String vietnameseMeaning = translationService.translateText(wordReturned, "en", "vi");
                vocabulary.setMeaning(vietnameseMeaning);


                for (Map<String, Object> meaning : meanings) {
                    if (meaning.get("partOfSpeech") != null || !meaning.get("partOfSpeech").equals("")) {
                        String partOfSpeech = (String) meaning.get("partOfSpeech");
                        if (partOfSpeech.equalsIgnoreCase("noun")) {
                            vocabulary.setType(VocabularyType.NOUN);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("verb")) {
                            vocabulary.setType(VocabularyType.VERB);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("adjective")) {
                            vocabulary.setType(VocabularyType.ADJECTIVE);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("adverb")) {
                            vocabulary.setType(VocabularyType.ADVERB);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("pronoun")) {
                            vocabulary.setType(VocabularyType.PRONOUN);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("preposition")) {
                            vocabulary.setType(VocabularyType.PREPOSITION);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("conjunction")) {
                            vocabulary.setType(VocabularyType.CONJUNCTION);
                            break;
                        } else if (partOfSpeech.equalsIgnoreCase("interjection")) {
                            vocabulary.setType(VocabularyType.INTERJECTION);
                            break;
                        } else {
                            vocabulary.setType(VocabularyType.UNKNOWN);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(vocabulary);
    }

    @Transactional
    public  void deleteVoca(VocabularyDelete vocabularyId) {
        try{
            userVocabularyRepository.deleteById(vocabularyId.getVocabularyId());
            System.out.println("UserVocabulary deleted");
        }
        catch (Exception e) {
            System.err.println("UserVocabulary not found");
        }
        try {
            lessonVocabularyRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
            System.out.println("LessonVocabulary deleted");
        }
        catch (Exception e) {
            System.err.println("LessonVocabulary not found");
        }
        try {
            answerRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
            System.out.println("Answer deleted");
        }
        catch (Exception e) {
            System.err.println("Answer not found");
        }
        try {
            questionRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
            System.out.println("Question deleted");
        }
        catch (Exception e) {
            System.err.println("Question not found");
        }
        try {
            vocabularyRepository.deleteVocaById(vocabularyId.getVocabularyId());
            System.out.println("Vocabulary deleted");
        }
        catch (Exception e) {
            System.err.println("Vocabulary not found");
        }


//        lessonVocabularyRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
//        answerRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
//        questionRepository.deleteByVocabularyId(vocabularyId.getVocabularyId());
//        vocabularyRepository.deleteVocaById(vocabularyId.getVocabularyId());
    }

    public Vocabulary updateVocabulary(Vocabulary vocabulary) {
        Optional<Vocabulary> vocabularyOptional = vocabularyRepository.findById(vocabulary.getId());
        if (vocabularyOptional.isPresent()) {
            Vocabulary vocabularyToUpdate = vocabularyOptional.get();
            vocabularyToUpdate.setWord(vocabulary.getWord());
            vocabularyToUpdate.setMeaning(vocabulary.getMeaning());
            vocabularyToUpdate.setPronunciation(vocabulary.getPronunciation());
            vocabularyToUpdate.setType(vocabulary.getType());
            vocabularyToUpdate.setImage(vocabulary.getImage());
            vocabularyToUpdate.setAudio(vocabulary.getAudio());
            return vocabularyRepository.save(vocabularyToUpdate);
        }
        return null;
    }
}
