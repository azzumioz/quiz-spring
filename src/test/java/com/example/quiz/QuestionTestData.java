package com.example.quiz;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;

import java.util.List;

public class QuestionTestData {

    public static final int START_SEQ = 100000;

    public static final int QUESTION1_ID = START_SEQ;
    public static final int QUESTION2_ID = START_SEQ + 1;
    public static final int QUESTION3_ID = START_SEQ + 2;

    public static final QuestionDTO QUESTION1 = new QuestionDTO(QUESTION1_ID, "Question 1", "What?");
    public static final Question QUESTION = new Question(QUESTION1_ID, "Question 1", "What?");
    public static final QuestionDTO QUESTION2 = new QuestionDTO(QUESTION2_ID, "Question 2", "Who?");
    public static final QuestionDTO QUESTION3 = new QuestionDTO(QUESTION3_ID, "Question 3", "Which?");

    public static final List<QuestionDTO> QUESTIONS_QUIZ1 = List.of(QUESTION1, QUESTION2, QUESTION3);

    public static QuestionDTO getUpdatedQuestion() {
        return new QuestionDTO(QUESTION1_ID, "Question update", "Description update");
    }

    public static QuestionDTO getCreatedQuestion() {
        return new QuestionDTO("New Question", "Description");
    }

}
