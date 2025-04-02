package com.ust.expense_tracking.service;

import com.ust.expense_tracking.config.JwtUtil;
import com.ust.expense_tracking.model.User;
import com.ust.expense_tracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User Registered successfully";
    }

    public String login(String username,String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new RuntimeException("invalid credentials");
        }
        return jwtUtil.generateToken(username);
    }
}
