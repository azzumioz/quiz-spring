package com.example.quiz.controller.answer;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.HistoryDTO;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.History;
import com.example.quiz.service.AnswerService;
import com.example.quiz.service.HistoryService;
import com.example.quiz.util.AnswerUtil;
import com.example.quiz.util.HistoryUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Answer controller")
@RestController
@RequestMapping("api/answer")
@PreAuthorize("hasAuthority('USER')")
public class ProfileAnswerController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileAnswerController.class);
    static final String REST_URL = "/api/answer";

    @Autowired
    private AnswerService answerService;
    @Autowired
    private HistoryService historyService;

    @Operation(summary = "Get answer with id = answerId")
    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable int answerId) {
        LOG.info("Get answer {}", answerId);
        Answer answer = answerService.getAnswerById(answerId);
        return new ResponseEntity<>(AnswerUtil.asTo(answer), HttpStatus.OK);
    }

    @Operation(summary = "Get answers for question with id = questionId")
    @GetMapping("/{questionId}/all")
    public ResponseEntity<List<AnswerDTO>> getAllAnswerToQuestion(@PathVariable int questionId) {
        LOG.info("Get answers for question {}", questionId);
        List<AnswerDTO> answerDTOList = AnswerUtil.asTo(answerService.getAllByQuestionId(questionId));
        return new ResponseEntity<>(answerDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Choose answer with id = answerId for question with id = questionId")
    @PostMapping("/{questionId}/{answerId}")
    public ResponseEntity<Object> UserPostAnswer(@PathVariable int questionId,
                                                 @PathVariable int answerId) {
        LOG.info("Choose answer {} for question {}", answerId, questionId);
        History history = historyService.save(questionId, answerId);
        HistoryDTO createHistory = HistoryUtil.asTo(history);
        return new ResponseEntity<>(createHistory, HttpStatus.OK);
    }


}
