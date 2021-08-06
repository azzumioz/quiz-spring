package com.example.quiz.auth.config.to;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;

@Data
public class UserTo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, updatable = false)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo user = (UserTo) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
