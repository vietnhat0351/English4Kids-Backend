package com.example.English4Kids_Backend.services;

import com.example.English4Kids_Backend.dtos.pixabayresponse.PixabayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PixabayService {
    private final RestTemplate restTemplate;
    @Value("${pixabay.api.key}")
    private String apiKey;

    public PixabayResponse searchImages(String query) {
        String url = "https://pixabay.com/api/?key=" + apiKey + "&q=" + query + "&image_type=photo" + "&per_page=10";
        return restTemplate.getForObject(url, PixabayResponse.class);
    }
}
