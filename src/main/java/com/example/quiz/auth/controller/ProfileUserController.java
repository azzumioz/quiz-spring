package com.example.quiz.auth.controller;

import com.example.quiz.auth.config.to.UserTo;
import com.example.quiz.auth.entity.Activity;
import com.example.quiz.auth.entity.Role;
import com.example.quiz.auth.entity.User;

import com.example.quiz.auth.exception.RoleNotFoundException;
import com.example.quiz.auth.exception.UserActivatedException;
import com.example.quiz.auth.exception.UserOrEmailExistsException;
import com.example.quiz.auth.repository.ActivityRepository;
import com.example.quiz.auth.service.EmailService;
import com.example.quiz.auth.service.UserDetailsImpl;
import com.example.quiz.auth.service.UserDetailsServiceImpl;
import com.example.quiz.auth.service.UserService;
import com.example.quiz.auth.utils.CookieUtil;
import com.example.quiz.auth.utils.JwtUtils;
import com.example.quiz.auth.utils.UserUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


import java.util.UUID;


import static com.example.quiz.auth.service.UserService.DEFAULT_ROLE;

@Tag(name = "User Authentication controller")
@RestController

@RequestMapping("/api/authentication")
@Transactional
public class ProfileUserController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileUserController.class);

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CookieUtil cookieUtil;
    private EmailService emailService;
    private UserDetailsServiceImpl userDetailsService;
    private ActivityRepository activityRepository;

    @Autowired
    public ProfileUserController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, CookieUtil cookieUtil, EmailService emailService, UserDetailsServiceImpl userDetailsService, ActivityRepository activityRepository) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.cookieUtil = cookieUtil;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
        this.activityRepository = activityRepository;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
        LOG.info("Register user {}", user.getEmail());

        if (userService.userExists(user.getUsername(), user.getEmail())) {
            throw new UserOrEmailExistsException("User or email already exists");
        }

        Role userRole = userService.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new RoleNotFoundException("Default Role USER not found"));
        user.getRoles().add(userRole);

        user.setPassword(encoder.encode(user.getPassword()));

        Activity activity = new Activity();
        activity.setUser(user);
        activity.setUuid(UUID.randomUUID().toString());
        user.setActivity(activity);

        userService.register(user, activity);
        emailService.sendActivationEmail(user.getEmail(), user.getUsername(), activity.getUuid());

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(user.activity.getUuid());
    }

    @Operation(summary = "Activate new user")
    @PostMapping("/activate-account")
    public ResponseEntity<Boolean> activateUser(@RequestBody String uuid) {
        Activity activity = activityRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Activity not found with uuid: " + uuid));

        if (activity.isActivated()) {
            throw new UserActivatedException("User already activated");
        }

        int updateCount = userService.activate(uuid);

        return ResponseEntity.ok(updateCount == 1);
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<UserTo> login(@Valid @RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isActivated()) {
            throw new DisabledException("User disabled");
        }
        String jwt = jwtUtils.generateJwt(userDetails.getUser());
        HttpCookie cookie = cookieUtil.createJwtCookie(jwt);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(HttpHeaders.SET_COOKIE, cookie.toString());
        UserTo userTo = UserUtil.asTo(userDetails.getUser());
        userTo.setPassword(null);
        return ResponseEntity.ok().headers(responseHeader).body(userTo);
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity logout() {
        HttpCookie cookie = cookieUtil.deleteJwtCookie();
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().headers(responseHeader).build();
    }

    @Operation(summary = "Update user password")
    @PostMapping("/update-password")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> updatePassword(@RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        int updatedCount = userService.updatePassword(encoder.encode(password), user.getUsername());
        return ResponseEntity.ok(updatedCount == 1);
    }

    @Operation(summary = "Update user profile")
    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserTo> updateProfile(@Valid @RequestBody UserTo userTo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (userTo.getUsername() != null) {
            user.setUsername(userTo.getUsername());
        }
        if (userTo.getEmail() != null) {
            user.setEmail(userTo.getEmail());
        }
        UserTo userUpdated = UserUtil.asTo(userService.update(user));
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @Operation(summary = "Get user profile")
    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserTo> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        UserTo userTo = UserUtil.asTo(user.getUser());
        return new ResponseEntity<>(userTo, HttpStatus.OK);
    }

    @Operation(summary = "Send mail with link activation")
    @PostMapping("/resend-activate-email")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity resendActivateEmail(@RequestBody String email) {
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
        Activity activity = activityRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Activity Not Found with user: " + user.getUsername()));
        if (activity.isActivated())
            throw new UserActivatedException("User already activated: " + user.getUsername());

        emailService.sendActivationEmail(user.getEmail(), user.getUsername(), activity.getUuid());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Send mail with link password reset")
    @PostMapping("/send-reset-password-email")
    public ResponseEntity sendEmailResetPassword(@RequestBody String email) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
        User user = userDetails.getUser();
        if (userDetails != null) {
            emailService.sendResetPasswordEmail(user.getEmail(), jwtUtils.createEmailResetToken(user));
        }
        return ResponseEntity.ok().build();
    }


}
