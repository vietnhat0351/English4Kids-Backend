package com.example.English4Kids_Backend.services;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class TextToSpeechService {
    public byte[] synthesizeTextToBytes(String text) {

        try {
            // Tải thông tin xác thực từ tệp JSON
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\vietn\\Downloads\\english4kids-435709-2885d357e515.json"))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            // Thiết lập TextToSpeechClient với thông tin xác thực
            TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(settings);

            // Cấu hình đầu vào (văn bản cần chuyển đổi)
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // Cấu hình giọng đọc (ví dụ: giọng nữ tiếng Anh Mỹ)
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .setName("en-US-Wavenet-C")
                    .build();

            // Cấu hình định dạng âm thanh
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Gọi API để chuyển đổi văn bản thành giọng nói
            var response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Trả về nội dung âm thanh dưới dạng byte[]
            ByteString audioContents = response.getAudioContent();
            return audioContents.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
