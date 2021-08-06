package com.example.quiz.controller.question;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;
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

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Question controller")
@RestController
@RequestMapping("api/question")
@PreAuthorize("hasAuthority('USER')")
public class ProfileQuestionController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileQuestionController.class);
    static final String REST_URL = "/api/question";

    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Get question with id = questionId")
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable int questionId) {
        LOG.info("Get question {} ", questionId);
        Question question = questionService.getQuestionById(questionId);
        return new ResponseEntity<>(QuestionUtil.asTo(question), HttpStatus.OK);
    }

    @Operation(summary = "Get all questions for quiz with id = quizId")
    @GetMapping("/{quizId}/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsToQuiz(@PathVariable("quizId") int quizId) {
        LOG.info("Get all question for quiz {} ", quizId);
        List<QuestionDTO> questionDTOList = questionService.getAllQuestionsByQuizId(quizId)
                .stream()
                .map(QuestionUtil::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

}
