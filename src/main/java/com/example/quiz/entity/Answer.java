package com.example.quiz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Question question;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private History history;

    public Answer() {
    }

    public Answer(String title, String description, Boolean isCorrect) {
        this.title = title;
        this.description = description;
        this.isCorrect = isCorrect;
    }

    public Answer(int id, String title, String description, Boolean isCorrect) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCorrect = isCorrect;
    }

    public Answer(String title, String description, Boolean isCorrect, Question question) {
        this.title = title;
        this.description = description;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id &&
                Objects.equals(title, answer.title) &&
                Objects.equals(description, answer.description) &&
                Objects.equals(isCorrect, answer.isCorrect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, isCorrect);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

}
