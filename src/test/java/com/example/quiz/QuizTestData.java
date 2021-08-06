package com.example.quiz;

import com.example.quiz.entity.Quiz;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class QuizTestData {

    private static final int START_SEQ = 100000;

    public static final int QUIZ1_ID = START_SEQ;
    private static final int QUIZ2_ID = START_SEQ + 1;
    private static final int QUIZ3_ID = START_SEQ + 2;

    public static final Quiz QUIZ1 = new Quiz(QUIZ1_ID, "Quiz 1", "Description Quiz 1", false, of(2021, Month.JULY, 1, 10, 0));
    public static final Quiz QUIZ2 = new Quiz(QUIZ2_ID, "Quiz 2", "Description Quiz 2", true, of(2021, Month.JULY, 1, 10, 0));
    public static final Quiz QUIZ3 = new Quiz(QUIZ3_ID, "Quiz 3", "Description Quiz 3", false, of(2021, Month.JULY, 1, 10, 0));
    public static final List<Quiz> QUIZZES = List.of(QUIZ1, QUIZ2, QUIZ3);

    public static Quiz getUpdatedQuiz() {
        return new Quiz(QUIZ1_ID, "Quiz update", "Description update", true);
    }

    public static Quiz getCreatedQuiz() {
        return new Quiz("New Quiz", "Description", false);
    }

}
