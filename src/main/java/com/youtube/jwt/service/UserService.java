package com.youtube.jwt.service;

import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.repository.RoleRepository;
import com.youtube.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }


}
