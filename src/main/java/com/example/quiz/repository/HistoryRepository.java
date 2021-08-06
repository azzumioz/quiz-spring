package com.example.quiz.repository;

import com.example.quiz.auth.entity.User;
import com.example.quiz.entity.History;
import com.example.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    int countHistoryByUserAndQuizAndIsCorrectTrue (User user, Quiz quiz);



}
