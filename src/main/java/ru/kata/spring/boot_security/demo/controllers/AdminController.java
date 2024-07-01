package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String showUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("authenticatedUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("authenticatedUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "user-create";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(
            @ModelAttribute("user") User user, @RequestParam("rolesList") String selectedRoles) {
        userService.addUser(user, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("updatedUser") User updatedUser,
                             @RequestParam(value = "userRoles", required = false) String userRoles) {
        userService.updateUser(updatedUser, userRoles);
        return "redirect:/admin";
    }
}
