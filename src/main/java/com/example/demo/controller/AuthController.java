
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return repository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = repository.findByEmail(user.getEmail())
                .orElseThrow();

        if (!existingUser.getSenha().equals(user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.generateToken(existingUser.getEmail());
    }
}
