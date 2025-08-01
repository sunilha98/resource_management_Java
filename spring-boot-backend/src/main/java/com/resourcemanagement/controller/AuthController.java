package com.resourcemanagement.controller;

import com.resourcemanagement.dto.AuthResponse;
import com.resourcemanagement.dto.LoginRequest;
import com.resourcemanagement.dto.UserDTO;
import com.resourcemanagement.entity.User;
import com.resourcemanagement.repository.UserRepository;
import com.resourcemanagement.security.JwtUtils;
import com.resourcemanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            User user = new User();
            user.setUsername(loginRequest.getUsername());
            user.setPassword(loginRequest.getPassword());
            User authResponse = authService.registerUser(user);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}