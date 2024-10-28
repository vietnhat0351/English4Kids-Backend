package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.dtos.TopicDTO;
import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.enums.VocabularyType;
import com.example.English4Kids_Backend.repositories.TopicRespository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final TopicRespository topicRespository;
    private final TranslationService translationService;

    public List<Vocabulary> getAllVocabularies() {
        return vocabularyRepository.findAll();
    }
    public List<TopicDTO> getAllTopics() {
        return topicRespository.findAllTopic();
    }
    public List<Vocabulary> getVocabulariesByTopic(Long topicId) {
        return vocabularyRepository.findByTopicId(topicId);
    }
    public Topic createTopic(Topic topic) {
        if (topic.getImage() == null || topic.getImage().equalsIgnoreCase("")){
            topic.setImage("https://english-for-kids.s3.ap-southeast-1.amazonaws.com/trends.gif");
        }
        topic.setVocabularies(new ArrayList<>());
        return topicRespository.save(topic);
    }
    public String deleteTopic(Long topicId) {
        topicRespository.deleteById(topicId);
        return "Deleted";
    }
    public Vocabulary createVocabulary(Vocabulary vocabulary) {
        Vocabulary vocabularies = vocabularyRepository.findByWord(vocabulary.getWord());
        if (vocabularies != null){
            return vocabularies;
        }
        return vocabularyRepository.save(vocabulary);
    }

    // Gọi API để lấy thông tin từ vựng từ DictionaryAPI hoặc API khác
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
                vocabulary.setVietnameseMeaning(vietnameseMeaning);


                for (Map<String, Object> meaning : meanings) {
                    if (meaning.get("partOfSpeech") != null || !meaning.get("partOfSpeech").equals("")){
                        String partOfSpeech = (String) meaning.get("partOfSpeech");
                        if (partOfSpeech.equalsIgnoreCase("noun")){
                            vocabulary.setType(VocabularyType.NOUN);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("verb")){
                            vocabulary.setType(VocabularyType.VERB);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("adjective")){
                            vocabulary.setType(VocabularyType.ADJECTIVE);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("adverb")){
                            vocabulary.setType(VocabularyType.ADVERB);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("pronoun")){
                            vocabulary.setType(VocabularyType.PRONOUN);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("preposition")){
                            vocabulary.setType(VocabularyType.PREPOSITION);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("conjunction")){
                            vocabulary.setType(VocabularyType.CONJUNCTION);
                            break;
                        }

                        else if (partOfSpeech.equalsIgnoreCase("interjection")){
                            vocabulary.setType(VocabularyType.INTERJECTION);
                            break;
                        }

                        else {
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

    // Hàm kiểm tra và lưu từ vựng vào database nếu chưa có
    public Vocabulary getOrCreateVocabulary(String word) {
        Optional<Vocabulary> vocabularyOptional = Optional.ofNullable(vocabularyRepository.findByWord(word));

        // Nếu từ vựng đã tồn tại trong database, trả về kết quả
        if (vocabularyOptional.isPresent()) {
            return vocabularyOptional.get();
        }

        // Nếu từ vựng chưa có, gọi API để lấy thông tin và lưu vào database
        Optional<Vocabulary> fetchedVocabulary = fetchVocabularyFromApi(word);
        return fetchedVocabulary.map(vocabularyRepository::save)
                .orElseThrow(() -> new RuntimeException("Failed to fetch or save vocabulary"));
    }

}
