package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> getAllUsers();

    void deleteById(Long id);

    void updateUser(User user, String role);

    User findByEmail(String email);

    void addUser(User user,String role);
}
