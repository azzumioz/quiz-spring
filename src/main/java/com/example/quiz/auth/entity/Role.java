package com.example.quiz.auth.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="ROLE_DATA")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

}
