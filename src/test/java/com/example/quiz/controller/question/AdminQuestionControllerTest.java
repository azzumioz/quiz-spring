package com.example.quiz.controller.question;

import com.example.quiz.AbstractControllerTest;
import com.example.quiz.QuestionTestData;
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;
import com.example.quiz.service.QuestionService;
import com.example.quiz.util.QuestionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.quiz.QuizTestData.*;

import java.util.List;

import static com.example.quiz.QuestionTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminQuestionControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminQuestionController.REST_URL + "/";

    @Autowired
    QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void createQuestion() throws Exception {
        QuestionDTO created = QuestionTestData.getCreatedQuestion();
        ResultActions result = mockMvc.perform(post(REST_URL + QUIZ1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andDo(print());
        Question returned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), Question.class);
        created.setId(returned.getId());
        assertArrayEquals(QuestionUtil.asTo(questionService.getAllQuestionsByQuizId(QUESTION1_ID)).toArray(), List.of(QUESTION1, QUESTION2, QUESTION3, created).toArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void updateQuestion() throws Exception {
        QuestionDTO updated = QuestionTestData.getUpdatedQuestion();
        mockMvc.perform(put(REST_URL + QUIZ1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteQuestion() throws Exception {
        mockMvc.perform(delete(REST_URL + QUESTION1_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'Question was deleted'}"))
                .andDo(print());
        assertArrayEquals(QuestionUtil.asTo(questionService.getAllQuestionsByQuizId(QUIZ1_ID)).toArray(), List.of(QUESTION2, QUESTION3).toArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'QuestionNotFoundException'}"));
    }


}
