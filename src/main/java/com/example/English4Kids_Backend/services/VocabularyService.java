package com.example.English4Kids_Backend.services;


import com.example.English4Kids_Backend.dtos.TopicDTO;
import com.example.English4Kids_Backend.entities.Topic;
import com.example.English4Kids_Backend.entities.Vocabulary;
import com.example.English4Kids_Backend.repositories.TopicRespository;
import com.example.English4Kids_Backend.repositories.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final TopicRespository topicRespository;

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
        return vocabularyRepository.save(vocabulary);
    }

    // Gọi API để lấy thông tin từ vựng từ DictionaryAPI hoặc API khác
    public Optional<Vocabulary> fetchVocabularyFromApi(String word) {
        // Giả sử đây là nơi bạn gọi API bên thứ 3
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(apiUrl, Object[].class);
            Object[] vocabInfo = response.getBody();

            // Giả sử vocabInfo[0] chứa thông tin cần thiết
//            if (vocabInfo != null && vocabInfo.length > 0) {
//                // Parse kết quả và lấy các thông tin cần thiết từ response JSON
//                String englishMeaning = ...; // Parse nghĩa tiếng Anh
//                String pronunciation = ...; // Parse phát âm
//                String audioUrl = ...; // Parse đường dẫn âm thanh
//
//                // Dịch nghĩa tiếng Anh sang tiếng Việt
//                String vietnameseMeaning = translateToVietnamese(englishMeaning);
//
//                Vocabulary vocabulary = Vocabulary.builder()
//                        .word(word)
//                        .meaning(englishMeaning)
//                        .vietnameseMeaning(vietnameseMeaning)
//                        .pronunciation(pronunciation)
//                        .audio(audioUrl)
//                        .build();
//
//                return Optional.of(vocabulary);
//            }
            System.out.println("vocabInfo = " + vocabInfo[0]);
            System.out.println("vocabInfo = " + vocabInfo[0].getWord());
        } catch (Exception e) {
            // Xử lý lỗi gọi API
            System.err.println("Failed to fetch vocabulary: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Hàm dịch nghĩa tiếng Anh sang tiếng Việt
    public String translateToVietnamese(String englishMeaning) {
        String apiUrl = "https://translation-api.example.com/translate?text=" + englishMeaning + "&target=vi";
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Gọi API dịch nghĩa
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            return response.getBody(); // Nghĩa tiếng Việt
        } catch (Exception e) {
            // Xử lý lỗi dịch
            System.err.println("Failed to translate: " + e.getMessage());
        }
        return englishMeaning; // Trả về nghĩa gốc nếu không dịch được
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
