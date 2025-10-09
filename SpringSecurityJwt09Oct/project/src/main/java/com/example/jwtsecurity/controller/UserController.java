package com.example.jwtsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> getUserProfile(Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User profile accessed");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> getUserDashboard(Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to user dashboard");
        response.put("username", authentication.getName());
        return ResponseEntity.ok(response);
    }
}
