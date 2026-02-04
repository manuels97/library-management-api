package com.library.api.service;

import com.library.api.dto.UserResponseDTO;
import com.library.api.exception.AlreadyExistsException;
import com.library.api.model.User;
import com.library.api.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new AlreadyExistsException("El nombre de usuario ya existe: " + user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() != null && !user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        }

        User savedUser = userRepo.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setUserRole(user.getRole());
        return dto;
    }
}