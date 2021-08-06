package com.example.quiz.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
public class AnswerDTO {
    @Hidden
    private int id;
    private String title;
    private String description;

    public AnswerDTO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

}
