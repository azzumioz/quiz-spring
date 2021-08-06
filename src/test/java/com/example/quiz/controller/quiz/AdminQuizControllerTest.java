package com.example.quiz.controller.quiz;

import com.example.quiz.AbstractControllerTest;
import com.example.quiz.QuizTestData;
import com.example.quiz.entity.Quiz;
import com.example.quiz.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.quiz.QuizTestData.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminQuizControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminQuizController.REST_URL + "/";

    @Autowired
    QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void createQuiz() throws Exception {
        Quiz created = QuizTestData.getCreatedQuiz();
        ResultActions result = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)));
        Quiz returned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), Quiz.class);
        created.setId(returned.getId());
        assertArrayEquals(quizService.getAllQuizzes().toArray(), List.of(QUIZ1, QUIZ2, QUIZ3, created).toArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void updateQuiz() throws Exception {
        Quiz updated = QuizTestData.getUpdatedQuiz();
        mockMvc.perform(put(REST_URL + QUIZ1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteQuiz() throws Exception {
        mockMvc.perform(delete(REST_URL + QUIZ1_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'Quiz was deleted'}"));
        assertArrayEquals(quizService.getAllQuizzes().toArray(), List.of(QUIZ2, QUIZ3).toArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuizNotFoundException'}"));
    }


}
