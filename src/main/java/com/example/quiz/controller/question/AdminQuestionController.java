package com.example.quiz.controller.question;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;
import com.example.quiz.payload.response.MessageResponse;
import com.example.quiz.service.QuestionService;
import com.example.quiz.util.QuestionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Administrator Question controller")
@RestController
@RequestMapping(value = AdminQuestionController.REST_URL)
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminQuestionController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminQuestionController.class);
    static final String REST_URL = "/api/admin/question";

    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Create question for quiz with id = quizId")
    @PostMapping("/{quizId}")
    public ResponseEntity<Object> createQuestion(@Valid @RequestBody QuestionDTO questionDTO,
                                                 @PathVariable int quizId) {
        LOG.info("Saving question for quiz {}", quizId);
        QuestionDTO createdQuestion = questionService.saveQuestion(quizId, questionDTO);
        return new ResponseEntity<>(createdQuestion, HttpStatus.OK);
    }

    @Operation(summary = "Update question with id = questionId")
    @PutMapping("/{questionId}")
    public ResponseEntity<Object> updateQuestion(@Valid @RequestBody QuestionDTO questionDTO, @PathVariable("questionId") int questionId) {
        LOG.info("Update question {}", questionId);
        questionDTO.setId(questionId);
        Question question = questionService.update(questionDTO);
        return new ResponseEntity<>(QuestionUtil.asTo(question), HttpStatus.OK);
    }

    @Operation(summary = "Delete question with id = questionId")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<MessageResponse> deleteQuestion(@PathVariable("questionId") String questionId) {
        LOG.info("Delete question {}", questionId);
        questionService.deleteQuestion(Integer.parseInt(questionId));
        return new ResponseEntity<>(new MessageResponse("Question was deleted"), HttpStatus.OK);
    }

}
