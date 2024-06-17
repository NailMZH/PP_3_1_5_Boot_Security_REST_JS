package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;

    //    @GetMapping("/")
//    public String homePage() {
//        return "home";
//    }
//    @GetMapping("/user")
//    public String userPage() {
//        return "user";
//    }


    @GetMapping("/unsecured")
    public String usecuredPage() {
        return "unsecured";
    }

    @GetMapping("/auth_page")
    public String authenticatedPage() {
        return "authenticated";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/user")
    public String userPage(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "Authenticated user info: " + user.getUsername() + " : " + user.getEmail();
    }
}