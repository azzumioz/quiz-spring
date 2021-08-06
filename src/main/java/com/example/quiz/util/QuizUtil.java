package com.example.quiz.util;

import com.example.quiz.dto.QuizDTO;
import com.example.quiz.entity.Quiz;
import java.util.List;
import java.util.stream.Collectors;

public class QuizUtil {
    public static QuizDTO asTo(Quiz quiz) {
        return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getDate(), quiz.isPublished());
    }

    public static List<QuizDTO> asTo(List<Quiz> quizzes) {
        return quizzes.stream().map(QuizUtil::asTo).collect(Collectors.toList());
    }
}
