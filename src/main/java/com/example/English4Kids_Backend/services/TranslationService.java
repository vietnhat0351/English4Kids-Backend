package com.example.English4Kids_Backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestTemplate restTemplate;
    private static final String MYMEMORY_API_URL = "https://api.mymemory.translated.net/get?q={text}&langpair={source}|{target}";
    public String translateText(String text, String sourceLang, String targetLang) {
        // Gửi yêu cầu GET tới MyMemory API
        String response = restTemplate.getForObject(MYMEMORY_API_URL, String.class, text, sourceLang, targetLang);

        // Chuyển đổi chuỗi phản hồi thành JSON để lấy kết quả dịch
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONObject("responseData").getString("translatedText");
    }


}
