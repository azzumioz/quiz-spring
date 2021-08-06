package com.example.quiz.auth.service;

import com.example.quiz.auth.entity.Activity;
import com.example.quiz.auth.entity.Role;
import com.example.quiz.auth.entity.User;
import com.example.quiz.auth.exception.UserNotFoundException;
import com.example.quiz.auth.repository.ActivityRepository;
import com.example.quiz.auth.repository.RoleRepository;
import com.example.quiz.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    public static final String DEFAULT_ROLE = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }

    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

    public void register(User user, Activity activity) {
        userRepository.save(user);
        activityRepository.save(activity);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean userExists(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            return true;
        }
        if (userRepository.existsByEmail(email)) {
            return true;
        }
        return false;
    }

    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }

    public Optional<Activity> findActivityByUuid(String uuid) {
        return activityRepository.findByUuid(uuid);
    }

    public int activate (String uuid) {
        return activityRepository.updateActivated(uuid, true);
    }

    public int updatePassword(String password, String username) {
        return userRepository.updatePassword(password, username);
    }

    public void delete (int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User cannot be found with id: " + userId));
        userRepository.delete(user);
    }
    
    public void delete (User user) {
        userRepository.delete(user);
    }

    public User update (User user) {
        return userRepository.save(user);
    }
}
