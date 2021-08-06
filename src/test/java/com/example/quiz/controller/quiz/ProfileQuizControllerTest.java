package com.example.quiz.controller.quiz;

import com.example.quiz.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.example.quiz.QuizTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileQuizControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileQuizController.REST_URL + "/";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getAllQuizzes() throws Exception {
        mockMvc.perform(get(REST_URL + "all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(QUIZZES.toArray())));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getById() throws Exception {
        mockMvc.perform(get(REST_URL + QUIZ1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(QUIZ1)));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuizNotFoundException'}"));
    }


}
