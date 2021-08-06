package com.example.quiz.controller.answer;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.entity.Answer;
import com.example.quiz.payload.response.MessageResponse;
import com.example.quiz.service.AnswerService;
import com.example.quiz.util.AnswerUtil;
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

@Tag(name = "Administrator Answer controller")
@RestController
@RequestMapping(value = AdminAnswerController.REST_URL)
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAnswerController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminAnswerController.class);
    static final String REST_URL = "/api/admin/answer";

    @Autowired
    private AnswerService answerService;

    @Operation(summary = "Create answer for question with id = questionId")
    @PostMapping("/{questionId}")
    public ResponseEntity<Object> createAnswer(@Valid @RequestBody Answer answer,
                                               @PathVariable int questionId) {
        LOG.info("Saving answer for question {}", questionId);
        return new ResponseEntity<>(answerService.saveAnswer(questionId, answer), HttpStatus.OK);
    }

    @Operation(summary = "Delete answer with id = answerId")
    @DeleteMapping("/{answerId}")
    public ResponseEntity<MessageResponse> deleteAnswer(@PathVariable int answerId) {
        LOG.info("Delete answer {}", answerId);
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(new MessageResponse("Answer was deleted"), HttpStatus.OK);
    }

    @Operation(summary = "Update answer with id = answerId")
    @PutMapping("/{answerId}")
    public ResponseEntity<Object> updateAnswer(@Valid @RequestBody AnswerDTO answerDTO, @PathVariable("answerId") int answerId) {
        LOG.info("Update answer {}", answerId);
        answerDTO.setId(answerId);
        Answer answer = answerService.update(answerDTO);
        return new ResponseEntity<>(AnswerUtil.asTo(answer), HttpStatus.OK);
    }


}
