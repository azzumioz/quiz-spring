package com.example.quiz.controller.answer;

import com.example.quiz.AbstractControllerTest;
import com.example.quiz.util.AnswerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.example.quiz.AnswerTestData.*;
import static com.example.quiz.QuestionTestData.QUESTION1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileAnswerControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileAnswerController.REST_URL + "/";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getAnswerById() throws Exception {
        mockMvc.perform(get(REST_URL + ANSWER1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(AnswerUtil.asTo(ANSWER1))));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'AnswerNotFoundException'}"));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getAnswersToQuestion() throws Exception {
        mockMvc.perform(get(REST_URL + QUESTION1_ID + "/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(AnswerUtil.asTo(ANSWER_QUESTION1).toArray())));
    }

    @Test
    @WithMockUser(username = "USER", authorities = {"USER"})
    void getAnswersToQuestionNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1 + "/all"))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuestionNotFoundException'}"));
    }


}
