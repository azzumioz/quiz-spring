package com.example.quiz.util;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionUtil {
    public static QuestionDTO asTo(Question question) {
        return new QuestionDTO(question.getId(), question.getTitle(), question.getDescription());
    }

    public static List<QuestionDTO> asTo(List<Question> questions) {
        return questions.stream().map(QuestionUtil::asTo).collect(Collectors.toList());
    }
}
