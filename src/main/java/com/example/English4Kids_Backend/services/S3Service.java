package com.example.English4Kids_Backend.services;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName = "english-for-kids";

    // Implement upload method
    public String upload(MultipartFile file) throws IOException {
        amazonS3.putObject(
                bucketName,
                file.getOriginalFilename(),
                file.getInputStream(), null);
        return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
    }
}
