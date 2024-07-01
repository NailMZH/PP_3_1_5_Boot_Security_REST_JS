package ru.kata.spring.boot_security.demo.service;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.*;



@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Map<String, String> roleMappings;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user, String role) {
        User userNotUpdate = findById(user.getId());
        userNotUpdate.setFirstName(user.getFirstName());
        userNotUpdate.setLastName(user.getLastName());
        userNotUpdate.setAge(user.getAge());
        userNotUpdate.setEmail(user.getEmail());
        if (!user.getPassword().equals("")) {
            userNotUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (role != null) {
            userNotUpdate.setRoles(roleService.getSetRoles(role));
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }
    @Override
    @Transactional(propagation = Propagation.NEVER)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addUser(User user, String role) {
        if (getByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(roleService.getSetRoles(role));
            userRepository.save(user);
        }
    }
}