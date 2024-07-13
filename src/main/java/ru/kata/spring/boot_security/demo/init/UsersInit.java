package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UsersInit {
    private final UserService userService;

    public UsersInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initializeUsers() {
        User user = userService.findByEmail("admin@mail.ru");
        if (user == null) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            List<Role> adminRoles = new ArrayList<>();
//            adminRoles.add(userRole);
            adminRoles.add(adminRole);
            User admin = new User( adminRoles,"xamz", "tutu", 20L, "admin@mail.ru", "100");

            userService.save(admin);

        }

    }
}

