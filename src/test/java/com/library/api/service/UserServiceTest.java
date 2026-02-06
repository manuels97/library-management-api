package com.library.api.service;

import com.library.api.exception.AlreadyExistsException;
import com.library.api.model.User;
import com.library.api.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("123");
        user.setRole("ADMIN");
    }

    @Test
    void registerUser_ShouldEncryptPasswordAndSave() {

        when(userRepo.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(userRepo.save(any(User.class))).thenReturn(user);


        userService.registerUser(user);


        verify(passwordEncoder, times(1)).encode("123");
        verify(userRepo, times(1)).save(any(User.class));
        assertEquals("hashed_password", user.getPassword());
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {

        when(userRepo.existsByUsername("testuser")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });

        verify(userRepo, never()).save(any(User.class));

        verify(passwordEncoder, never()).encode(anyString());
    }
}