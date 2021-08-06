package com.example.quiz;

import com.example.quiz.auth.entity.User;

import java.util.List;

public class UserTestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final User USER = new User("User", "user@gmail.com", "password");
    public static final User USER1 = new User(1, "USER1", "user1@gmail.com", "password");
    public static final User USER2 = new User(2, "USER2", "user2@gmail.com", "password");
    public static final User USER3 = new User(3, "USER3", "user3@gmail.com", "password");
    public static final User ADMIN = new User("Admin", "admin@gmail.com", "password");
    public static final List<User> USERS = List.of(USER1, USER2, USER3);

    public static User getNew() {
        return new User("New", "new@gmail.com", "newPass");
    }

}
