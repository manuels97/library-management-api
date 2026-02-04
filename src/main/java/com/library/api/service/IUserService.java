package com.library.api.service;

import com.library.api.dto.UserResponseDTO;
import com.library.api.model.User;
import java.util.List;

public interface IUserService {
    UserResponseDTO registerUser(User user);
    List<UserResponseDTO> getAllUsers();
}