package com.example.quiz.service;

import com.example.quiz.auth.entity.User;
import com.example.quiz.auth.service.UserDetailsImpl;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.History;
import com.example.quiz.entity.Quiz;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.exception.QuizNotFoundException;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.HistoryRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    public History save(int questionId, int answerId) {
        questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question cannot be found with id: " + questionId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        History history = new History();
        Answer answer = answerRepository.findAnswerById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer cannot be found with id: " + answerId));

        history.setAnswer(answer);
        history.setUser(user);
        history.setQuestion(answer.getQuestion());
        history.setQuiz(answer.getQuestion().getQuiz());
        history.setIsCorrect(answer.getIsCorrect());
        return historyRepository.save(history);
    }

    public List<History> getHistoryAll() {
        return historyRepository.findAll();
    }

    public int getScore(int quizId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Quiz quiz = quizRepository.findQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + quizId));
        return historyRepository.countHistoryByUserAndQuizAndIsCorrectTrue(user, quiz);
    }

}
