package com.example.quiz.util;

import com.example.quiz.dto.HistoryDTO;
import com.example.quiz.entity.History;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryUtil {
    public static HistoryDTO asTo(History history) {
        return new HistoryDTO(history.getId(), history.getQuiz().getId(), history.getQuestion().getId(), history.getAnswer().getId(), history.getUser().getId(), history.getIsCorrect());
    }

    public static List<HistoryDTO> asTo(List<History> historyList) {
        return historyList.stream().map(HistoryUtil::asTo).collect(Collectors.toList());
    }
}
