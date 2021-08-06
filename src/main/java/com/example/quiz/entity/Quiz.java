package com.example.quiz.entity;

import com.example.quiz.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean published;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "quiz", orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(int id, String title, String description, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public Quiz(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public Quiz(int id, String title, String description, boolean published, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id &&
                published == quiz.published &&
                title.equals(quiz.title) &&
                description.equals(quiz.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, published);
    }
}
