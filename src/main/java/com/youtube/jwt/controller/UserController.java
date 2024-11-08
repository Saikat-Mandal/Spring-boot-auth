package com.youtube.jwt.controller;

import com.youtube.jwt.dto.LoginDto;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.response.JwtAuthResponse;
import com.youtube.jwt.service.AuthService;
import com.youtube.jwt.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;
    private final AuthService authService;


//    test ADMIN ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin");
    }

//    test USER ROLE
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> helloUser(){
        return ResponseEntity.ok("Hello User");
    }

//    get user by id
    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable Integer userId) {
        return "This endpoint is accessible without specific role";
    }

//    get all users
    @GetMapping("/user")
    public String getAllUsers(@PathVariable Integer userId) {
        return "This endpoint is accessible without specific role";
    }

//    delete a user
    @DeleteMapping("/user/{userId}")
    public String deleteUserById(@PathVariable Integer userId) {
        return "This endpoint is accessible without specific role";
    }

}
