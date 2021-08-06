package com.example.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuestionDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    @Schema(description = "Title", example = "Test 1")
    private String title;
    @Schema(description = "Description", example = "Test Math")
    private String description;

    public QuestionDTO() {
    }

    public QuestionDTO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public QuestionDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
}
