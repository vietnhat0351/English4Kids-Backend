package com.example.English4Kids_Backend.services;

import java.time.LocalDate;

public class SpacedRepetitionService {
    public LocalDate calculateNextReviewDate(int count, LocalDate lastAccessed) {
        int daysToAdd;

        switch (count) {
            case 1:
                daysToAdd = 1; // 1 ngày sau lần học đầu tiên
                break;
            case 2:
                daysToAdd = 3; // 3 ngày sau lần học thứ hai
                break;
            case 3:
                daysToAdd = 7; // 7 ngày sau lần học thứ ba
                break;
            case 4:
                daysToAdd = 14; // 14 ngày sau lần học thứ tư
                break;
            case 5:
                daysToAdd = 30; // 30 ngày sau lần học thứ năm
                break;
            default:
                daysToAdd = 60; // 60 ngày sau các lần học tiếp theo
                break;
        }

        return lastAccessed.plusDays(daysToAdd);
    }
}
