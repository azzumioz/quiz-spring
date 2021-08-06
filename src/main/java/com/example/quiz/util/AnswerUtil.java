package com.example.quiz.util;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.entity.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class AnswerUtil {
    public static AnswerDTO asTo(Answer answer) {
        return new AnswerDTO(answer.getId(), answer.getTitle(), answer.getDescription());
    }

    public static List<AnswerDTO> asTo(List<Answer> answers) {
        return answers.stream().map(AnswerUtil::asTo).collect(Collectors.toList());
    }
}
