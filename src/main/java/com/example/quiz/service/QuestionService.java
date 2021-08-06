package com.example.quiz.service;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.exception.QuizNotFoundException;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.util.QuestionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question getQuestionById(int id) {
        Question question = questionRepository.findQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question cannot be found with id: " + id));
        return question;
    }

    public QuestionDTO saveQuestion(int quizId, QuestionDTO questionDTO) {
        Quiz quiz = quizRepository.findQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + quizId));
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        question.setQuiz(quiz);
        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        return QuestionUtil.asTo(questionRepository.save(question));
    }

    public Question update(QuestionDTO questionDTO) {
        Assert.notNull(questionDTO, "question must not by null");
        questionRepository.findQuestionById(questionDTO.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question cannot be found with id: " + questionDTO.getId()));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestionsByQuizId(int quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz cannot be found with id: " + quizId));
        List<Question> questions = questionRepository.findAllByQuiz(quiz);
        return questions;
    }

    public void deleteQuestion(int questionId) {
        Question question = questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question cannot be found with id: " + questionId));
        questionRepository.delete(question);

    }

}
