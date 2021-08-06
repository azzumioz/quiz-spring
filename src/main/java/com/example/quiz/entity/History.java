package com.example.quiz.entity;

import com.example.quiz.auth.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private User user;

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Question question;

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Quiz quiz;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @Column
    private Boolean isCorrect;

    public History() {
    }

    public History(int id, Answer answer, User user, Question question, Quiz quiz) {
        this.id = id;
        this.answer = answer;
        this.user = user;
        this.question = question;
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", date=" + date +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
