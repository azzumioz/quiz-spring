package com.example.quiz.controller.quiz;

import com.example.quiz.dto.QuizDTO;
import com.example.quiz.entity.Quiz;
import com.example.quiz.service.QuizService;

import com.example.quiz.util.QuizUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Quiz controller")
@RestController
@RequestMapping(value = ProfileQuizController.REST_URL)
public class ProfileQuizController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileQuizController.class);
    static final String REST_URL = "/api/quiz";

    @Autowired
    private QuizService quizService;

    @Operation(summary = "Get all quizzes")
    @GetMapping("/all")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        LOG.info("Get all quizzes");
        List<QuizDTO> quizDTOList = quizService.getAllQuizzes()
                .stream()
                .map(QuizUtil::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(quizDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Get quiz with id = quizId")
    @GetMapping("/{quizId}")
    public ResponseEntity<Object> getQuizById(@PathVariable int quizId) {
        LOG.info("Get quiz {}", quizId);
            Quiz quiz = quizService.getQuizById(quizId);
            return new ResponseEntity<>(QuizUtil.asTo(quiz), HttpStatus.OK);
    }

}
