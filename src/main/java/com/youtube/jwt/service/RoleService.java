package com.youtube.jwt.service;

import com.youtube.jwt.entity.Role;
import com.youtube.jwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createRole(Role role) {
       return roleRepository.save(role);
    }
}
