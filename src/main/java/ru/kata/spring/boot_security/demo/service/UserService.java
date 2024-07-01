package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> getAllUsers();

    void deleteById(Long id);

    User getUserByUsername(String username);

//    public void save(User user);

    //    Map<User, String> createUsersCollection(List<User> users);
//    User getByEmail(String email);
    void updateUser(User user, String role);

//    Map<User, String> createUserCollection(List<User> users);
    User getByEmail(String email);

    void addUser(User user,String role);
}
