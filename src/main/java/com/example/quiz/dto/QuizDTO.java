package com.example.quiz.dto;

import com.example.quiz.util.DateTimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
public class QuizDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime date;
    private boolean published;

    public QuizDTO(int id, String title, String description, LocalDateTime date, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.published = published;
    }


}
