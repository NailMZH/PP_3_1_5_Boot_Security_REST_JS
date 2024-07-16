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
//            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            List<Role> adminRoles = new ArrayList<>();
//            adminRoles.add(userRole);
            adminRoles.add(adminRole);
            User admin = new User( adminRoles,"Ivan", "Petrov", 20L, "admin@mail.ru", "100");

            userService.saveUser(admin);

        }

        User user1 = userService.findByEmail("user@mail.ru");
        if (user1 == null) {
            Role userRole = new Role("ROLE_USER");

            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(userRole);
            User user2 = new User( adminRoles,"Nail", "Mazhitov", 40L, "user@mail.ru", "100");

            userService.saveUser(user2);
        }

    }
}

