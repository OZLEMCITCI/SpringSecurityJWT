package com.springjwt.controller;


import com.springjwt.regres.LoginRequest;
import com.springjwt.regres.MesajResponse;
import com.springjwt.regres.RegisterRequest;
import com.springjwt.repository.KisiRepository;
import com.springjwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

//    @Autowired
//    KisiRepository kisiRepository;

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginToSystem(@RequestBody LoginRequest loginRequest){
        return authService.getUserInfo(loginRequest);
    }
}
