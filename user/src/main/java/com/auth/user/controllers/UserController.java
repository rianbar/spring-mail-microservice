package com.auth.user.controllers;

import com.auth.user.configs.SecurityConfigApp;
import com.auth.user.dtos.AuthDTO;
import com.auth.user.dtos.RegisterDTO;
import com.auth.user.models.UserModel;
import com.auth.user.repositories.UserRepository;
import com.auth.user.services.EmailService;
import com.auth.user.services.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@Slf4j
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @PostMapping(value = SecurityConfigApp.REGISTER_ROUTE)
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterDTO registerDTO) {

        if (this.userRepository.findByUsername(registerDTO.getUsername()) != null) {
            log.info("not null user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this user has already registered");}

        String passwordEncoder = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
        UserModel user = new UserModel(
                registerDTO.getUsername(), registerDTO.getEmail(), passwordEncoder, registerDTO.getRole());

        emailService.sendMailToRecipient(registerDTO.getUsername(), registerDTO.getEmail());
        this.userRepository.save(user);
        log.info("user registered");

        return ResponseEntity.status(HttpStatus.CREATED).body("user successfully registered");
    }

    @PostMapping(value = SecurityConfigApp.LOGIN_ROUTE)
    public ResponseEntity<Object> loginUser(@RequestBody @Valid AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authDTO.username(), authDTO.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
