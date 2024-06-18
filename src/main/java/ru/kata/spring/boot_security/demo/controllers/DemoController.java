package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
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

    @GetMapping(value = "/user_6")
    public String printUser(Principal principal, ModelMap model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user_0", user);
        return "users_1";
    }


}