package com.library.api.repository;

import com.library.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    void saveUser_ShouldWork() {

        User user = new User();
        user.setUsername("db_test_user");
        user.setPassword("123");
        user.setRole("ADMIN");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("db_test_user", savedUser.getUsername());
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUserExists() {

        User user = new User();
        user.setUsername("exists_user");
        user.setPassword("123");
        user.setRole("USER");
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("exists_user");


        assertTrue(exists);
    }
}