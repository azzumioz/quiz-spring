package com.example.quiz.auth.controller;

import com.example.quiz.auth.config.to.UserTo;
import com.example.quiz.auth.exception.UserNotFoundException;
import com.example.quiz.auth.objects.JsonException;
import com.example.quiz.auth.repository.ActivityRepository;
import com.example.quiz.auth.service.EmailService;
import com.example.quiz.auth.service.UserDetailsServiceImpl;
import com.example.quiz.auth.service.UserService;
import com.example.quiz.auth.utils.CookieUtil;
import com.example.quiz.auth.utils.JwtUtils;
import com.example.quiz.auth.utils.UserUtil;
import com.example.quiz.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Administrator Authentication controller")
@RestController
@RequestMapping("/api/admin/users")
@Transactional
public class AdminUserController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminUserController.class);

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CookieUtil cookieUtil;
    private EmailService emailService;
    private UserDetailsServiceImpl userDetailsService;
    private ActivityRepository activityRepository;

    @Autowired
    public AdminUserController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, CookieUtil cookieUtil, EmailService emailService, UserDetailsServiceImpl userDetailsService, ActivityRepository activityRepository) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.cookieUtil = cookieUtil;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
        this.activityRepository = activityRepository;
    }

    @Operation(summary = "Get all users")
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserTo>> getAllUsers() {
        LOG.info("Get all users");
        List<UserTo> userDTOList = userService.getAllUsers()
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Get user with id = userId")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserTo> getUser(@PathVariable int userId) {
        LOG.info("Get user {}", userId);
        UserTo userTo = UserUtil.asTo(userService.getUser(userId));
        return new ResponseEntity<>(userTo, HttpStatus.OK);
    }

    @Operation(summary = "Delete user with id = userId")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable int userId) {
        LOG.info("Delete user {}", userId);
        try {
            userService.delete(userId);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new MessageResponse("User with id = " + userId + " not found"), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(new MessageResponse("User was deleted"), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleExceptions(Exception ex) {
        return new ResponseEntity(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }

}
