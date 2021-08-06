package com.example.quiz.controller.question;

import com.example.quiz.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.example.quiz.QuestionTestData.*;
import static com.example.quiz.QuizTestData.QUIZ1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileQuestionControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileQuestionController.REST_URL + "/";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getQuestionById() throws Exception {
        mockMvc.perform(get(REST_URL + QUESTION1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(QUESTION1)));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getQuestionsToQuiz() throws Exception {
        mockMvc.perform(get(REST_URL + QUIZ1_ID + "/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(QUESTIONS_QUIZ1.toArray())));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getQuestionsToQuizNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1 + "/all"))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuizNotFoundException'}"));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuestionNotFoundException'}"));
    }


}
