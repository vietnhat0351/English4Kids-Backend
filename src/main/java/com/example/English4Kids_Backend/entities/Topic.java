package com.example.English4Kids_Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    private long topicId;
    private String name;
    private String image;

    @OneToMany(mappedBy = "topic")
    @JsonIgnore
    private List<Vocabulary> vocabularies;


}
