package com.example.quiz.controller.answer;

import com.example.quiz.AbstractControllerTest;
import com.example.quiz.AnswerTestData;
import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.entity.Answer;
import com.example.quiz.service.AnswerService;
import com.example.quiz.util.AnswerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.quiz.AnswerTestData.*;
import static com.example.quiz.QuestionTestData.QUESTION1_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminAnswerControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminAnswerController.REST_URL + "/";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AnswerService answerService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void createAnswer() throws Exception {
        Answer created = AnswerTestData.getCreatedAnswer();
        ResultActions result = mockMvc.perform(post(REST_URL + QUESTION1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andDo(print());
        Answer returned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), Answer.class);
        created.setId(returned.getId());
        assertArrayEquals((answerService.getAllByQuestionId(QUESTION1_ID)).toArray(), List.of(ANSWER1, ANSWER2, ANSWER3, created).toArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void updateAnswer() throws Exception {
        AnswerDTO updated = AnswerUtil.asTo(AnswerTestData.getUpdatedAnswer());
        mockMvc.perform(put(REST_URL + ANSWER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteAnswer() throws Exception {
        mockMvc.perform(delete(REST_URL + ANSWER1_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'Answer was deleted'}"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1))
                .andDo(print())
                .andExpect(content().json("{'exception':'AnswerNotFoundException'}"));
    }


}
