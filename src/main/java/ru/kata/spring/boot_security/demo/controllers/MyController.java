package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Controller
public class MyController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("msgs", userRepository.findAll());
        return "userhome";
    }

    @PostMapping("/messages")
    public String saveMessage(User user) {
        userRepository.save(user);
        return "redirect:/home";
    }
}
