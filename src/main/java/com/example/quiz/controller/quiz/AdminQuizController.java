package com.example.quiz.controller.quiz;

import com.example.quiz.dto.QuizDTO;
import com.example.quiz.entity.Quiz;
import com.example.quiz.payload.response.MessageResponse;
import com.example.quiz.service.QuizService;
import com.example.quiz.util.QuizUtil;
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

@Tag(name = "Administrator Quiz controller")
@RestController
@RequestMapping(value = AdminQuizController.REST_URL)
public class AdminQuizController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminQuizController.class);
    static final String REST_URL = "/api/admin/quiz";

    @Autowired
    private QuizService quizService;

    @Operation(summary = "Create new quiz")
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> createQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        LOG.info("Saving quiz");
        Quiz quiz = quizService.saveQuiz(quizDTO);
        return new ResponseEntity<>(QuizUtil.asTo(quiz), HttpStatus.OK);
    }

    @Operation(summary = "Update quiz with id = quizId")
    @PutMapping("/{quizId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateQuiz(@Valid @RequestBody QuizDTO quizDTO, @PathVariable("quizId") int quizId) {
        LOG.info("Update quiz {}", quizId);
        quizDTO.setId(quizId);
        Quiz quiz = quizService.update(quizDTO);
        return new ResponseEntity<>(QuizUtil.asTo(quiz), HttpStatus.OK);
    }

    @Operation(summary = "Delete quiz with id = quizId")
    @DeleteMapping("/{quizId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> deleteQuiz(@PathVariable int quizId) {
        LOG.info("Delete quiz {}", quizId);
        quizService.deleteQuiz(quizId);
        return new ResponseEntity<>(new MessageResponse("Quiz was deleted"), HttpStatus.OK);
    }


}
