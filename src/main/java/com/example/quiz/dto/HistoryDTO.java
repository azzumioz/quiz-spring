package com.example.quiz.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDTO {
    @Hidden
    private int id;
    private int userID;
    private int quizID;
    private int questionID;
    private int answerID;
    private boolean isCorrect;
    private LocalDateTime date;

    public HistoryDTO(int historyId, int quizId, int questionId, int answerId, Integer userId, Boolean isCorrect) {
    }

}
