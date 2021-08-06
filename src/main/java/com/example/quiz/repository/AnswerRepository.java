package com.example.quiz.repository;

import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findAllByQuestion(Question question);

    Optional<Answer> findAnswerById (int answerId);
//    Answer findAnswerById (int answerId);
}
