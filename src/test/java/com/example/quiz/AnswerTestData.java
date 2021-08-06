package com.example.quiz;
import com.example.quiz.entity.Answer;

import java.util.List;

import static com.example.quiz.QuestionTestData.QUESTION;

public class AnswerTestData {

    public static final int START_SEQ = 100000;

    public static final int ANSWER1_ID = START_SEQ;
    public static final int ANSWER2_ID = START_SEQ + 1;
    public static final int ANSWER3_ID = START_SEQ + 2;

    public static final Answer ANSWER1 = new Answer(ANSWER1_ID, "Answer 1", "One", false);
    public static final Answer ANSWER2 = new Answer(ANSWER2_ID, "Answer 2", "Two", true);
    public static final Answer ANSWER3 = new Answer(ANSWER3_ID, "Answer 3", "Three", false);
    public static final Answer ANSWER4 = new Answer("Answer 1", "One", false);
    public static final Answer ANSWER5 = new Answer("Answer 2", "Two", true);
    public static final Answer ANSWER6 = new Answer("Answer 3", "Three", false);
    public static final Answer ANSWER7 = new Answer("Answer 1", "One", false);
    public static final Answer ANSWER8 = new Answer("Answer 2", "Two", false);
    public static final Answer ANSWER9 = new Answer("Answer 3", "Three", true);
    public static final List<Answer> ANSWER_QUESTION1 = List.of(ANSWER1, ANSWER2, ANSWER3);

    public static Answer getUpdatedAnswer() {
        return new Answer (ANSWER1_ID, "Answer update", "Description update", true);
    }

    public static Answer getCreatedAnswer() {
        return new Answer ("New Answer", "Description", false, QUESTION);
    }

}
