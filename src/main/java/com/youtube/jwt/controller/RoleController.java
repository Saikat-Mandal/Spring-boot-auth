package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Role;
import com.youtube.jwt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/role")
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping("/roles")
    public String getAllRoles() {
        return "jello";
    }
}
