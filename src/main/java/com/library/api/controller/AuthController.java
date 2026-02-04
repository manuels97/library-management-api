package com.library.api.controller;

import com.library.api.dto.UserResponseDTO;
import com.library.api.model.User;
import com.library.api.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userServ;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody User user) {
        UserResponseDTO response = userServ.registerUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userServ.getAllUsers());
    }
}