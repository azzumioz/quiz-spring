package com.example.quiz.auth.utils;

import com.example.quiz.auth.config.to.UserTo;
import com.example.quiz.auth.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getUsername(), userTo.getEmail().toLowerCase(), "");
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getUsername(), user.getEmail(), "");
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setUsername(userTo.getUsername());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

}
