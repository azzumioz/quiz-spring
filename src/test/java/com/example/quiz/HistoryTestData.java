package com.example.quiz;
import com.example.quiz.entity.History;

import java.util.List;

import static com.example.quiz.AnswerTestData.*;
import static com.example.quiz.QuestionTestData.*;
import static com.example.quiz.QuizTestData.QUIZ1;
import static com.example.quiz.UserTestData.USER;


public class HistoryTestData {

    public static final int START_SEQ = 100000;

    public static final int HISTORY1_ID = START_SEQ;
    public static final int HISTORY2_ID = START_SEQ + 1;
    public static final int HISTORY3_ID = START_SEQ + 2;

    public static final History HISTORY1 = new History(HISTORY1_ID, ANSWER1, USER, QUESTION, QUIZ1);
    public static final History HISTORY2 = new History(HISTORY2_ID, ANSWER2, USER, QUESTION, QUIZ1);
    public static final History HISTORY3 = new History(HISTORY3_ID, ANSWER3, USER, QUESTION, QUIZ1);

    public static final List<History> HISTORIES = List.of(HISTORY1, HISTORY2, HISTORY3);
}
