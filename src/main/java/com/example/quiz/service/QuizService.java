package com.example.quiz.service;

import com.example.quiz.auth.entity.User;
import com.example.quiz.auth.service.UserDetailsImpl;
import com.example.quiz.dto.QuizDTO;
import com.example.quiz.entity.Quiz;
import com.example.quiz.exception.QuizNotFoundException;
import com.example.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz saveQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(int id) {
        Quiz quiz = quizRepository.findQuizById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + id));
        return quiz;
    }

    public void deleteQuiz(int quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + quizId));
        quizRepository.delete(quiz);
    }

    public Quiz update(QuizDTO quizDTO) {
        Assert.notNull(quizDTO, "quiz must not by null");
        quizRepository.findQuizById(quizDTO.getId())
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + quizDTO.getId()));
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setPublished(quizDTO.isPublished());
        return quizRepository.save(quiz);
    }

    public void complete (int quizId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

    }


}
