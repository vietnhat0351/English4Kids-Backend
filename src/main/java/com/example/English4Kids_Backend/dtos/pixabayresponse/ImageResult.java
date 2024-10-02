package com.example.English4Kids_Backend.dtos.pixabayresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResult {
    // example
    /*"id": 6316445,
            "pageURL": "https://pixabay.com/photos/rapeseeds-yellow-flowers-6316445/",
            "type": "photo",
            "tags": "rapeseeds, yellow, flowers",
            "previewURL": "https://cdn.pixabay.com/photo/2021/06/06/21/55/rapeseeds-6316445_150.jpg",
            "previewWidth": 150,
            "previewHeight": 100,
            "webformatURL": "https://pixabay.com/get/gd310599002e6eb61c35328d6e222346072d8b7e91c2375cfb21a8750d5e8410c1ac88c5632ca0d773cbc0872b54850981da8ecbb28b0eac44174898335144812_640.jpg",
            "webformatWidth": 640,
            "webformatHeight": 427,
            "largeImageURL": "https://pixabay.com/get/gd9dcf64f93b9f17f087db5a4d62d106782464fd216e2c57f8dbf4bef9a33315d0c81fab7a1b6876dc26583df382704f4b03be492415d3c58bd40a078102a8d18_1280.jpg",
            "imageWidth": 6000,
            "imageHeight": 4000,
            "imageSize": 7735260,
            "views": 35334,
            "downloads": 28335,
            "collections": 45,
            "likes": 82,
            "comments": 19,
            "user_id": 11378535,
            "user": "__Tatius__",
            "userImageURL": "https://cdn.pixabay.com/user/2020/10/16/11-47-36-873_250x250.jpeg" */
    private int id;
    private String pageURL;
    private String type;
    private String tags;
    private String previewURL;
    private int previewWidth;
    private int previewHeight;
    private String webformatURL;
    private int webformatWidth;
    private int webformatHeight;
    private String largeImageURL;
    private int imageWidth;
    private int imageHeight;
    private int imageSize;
    private int views;
    private int downloads;
    private int collections;
    private int likes;
    private int comments;
    private int user_id;
    private String user;
    private String userImageURL;
}
