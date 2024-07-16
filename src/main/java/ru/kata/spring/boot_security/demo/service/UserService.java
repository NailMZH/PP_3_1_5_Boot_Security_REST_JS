package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

  @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    List<User> getAllUsers();

    User findById(Long id);

    void deleteById(Long id);

    void update(User user);

    User findByEmail(String email);

    void saveUser(User user);

    List<Role> listRoles();
}
