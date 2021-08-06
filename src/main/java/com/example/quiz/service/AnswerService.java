package com.example.quiz.service;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Question;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.exception.QuizNotFoundException;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer getAnswerById(int id) {
        Answer answer = answerRepository.findAnswerById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer cannot be found with id: " + id));
        return answer;
    }

    public Answer saveAnswer(int questionId, Answer answer) {
        Question question = questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuizNotFoundException("Question cannot be found with id: " + questionId));
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    public Answer update (AnswerDTO answerDTO) {
        Assert.notNull(answerDTO, "Answer must not by null");
        answerRepository.findAnswerById(answerDTO.getId())
                .orElseThrow(() -> new AnswerNotFoundException("Answer cannot be found with id: " + answerDTO.getId()));
        Answer answer = new Answer();
        answer.setId(answerDTO.getId());
        answer.setTitle(answerDTO.getTitle());
        answer.setDescription(answerDTO.getDescription());
        return answerRepository.save(answer);
    }

    public List<Answer> getAllByQuestionId (int questionId) {
        Question question = questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question cannot be found with id: " + questionId));
        List<Answer> answers = answerRepository.findAllByQuestion(question);
        return answers;
    }

    public void deleteAnswer(int answerId) {
        Answer answer = answerRepository.findAnswerById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer cannot be found with id: " + answerId));
        answerRepository.delete(answer);
    }

}
