package com.example.English4Kids_Backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_matching_record")
@Builder
public class CardMatchingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private long userId;
    private long timeRecord;
    private int playCount;
}
