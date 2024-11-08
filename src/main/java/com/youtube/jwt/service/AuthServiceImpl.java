package com.youtube.jwt.service;

import com.youtube.jwt.dto.LoginDto;
import com.youtube.jwt.dto.RegisterDto;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.repository.RoleRepository;
import com.youtube.jwt.repository.UserRepository;
import com.youtube.jwt.response.JwtAuthResponse;
import com.youtube.jwt.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

//    login
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDto.getUsernameOrEmail(),
               loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String username = authentication.getName(); // Get the username from the authentication object

        return new JwtAuthResponse(token, "Bearer", username);
    }

//    register
    public User register(RegisterDto registerDto){
        // Check if username or email already exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new User object
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Assign default ROLE_USER role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Specified Role not found in the database"));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

}

