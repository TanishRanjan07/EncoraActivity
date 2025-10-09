package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.model.User;
import com.example.jwtsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getAdminDashboard(Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to admin dashboard");
        response.put("username", authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalUsers = userRepository.count();
        long adminCount = userRepository.findAll().stream()
                .filter(user -> "ADMIN".equals(user.getRole()))
                .count();
        long userCount = userRepository.findAll().stream()
                .filter(user -> "USER".equals(user.getRole()))
                .count();

        stats.put("totalUsers", totalUsers);
        stats.put("adminCount", adminCount);
        stats.put("userCount", userCount);

        return ResponseEntity.ok(stats);
    }
}
