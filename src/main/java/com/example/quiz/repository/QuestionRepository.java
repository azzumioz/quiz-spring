package com.example.quiz.repository;

import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByQuiz(Quiz quiz);

    Optional<Question> findQuestionById(int questionId);

    List<Question> findAll();
}
