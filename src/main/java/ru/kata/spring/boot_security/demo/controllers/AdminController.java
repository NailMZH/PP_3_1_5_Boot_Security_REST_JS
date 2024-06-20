package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("usersShow", users);
        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser( Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allroles", roleService.getRoles());
        return "user-create";
    }
    @PostMapping("/saveUser")
    public String saveNewUser(
            @ModelAttribute("user") User user,@RequestParam("selectedRoles") String[] selectedRoles) {
        userService.saveUser(user, selectedRoles);
        return "redirect:/admin";
    }
    @GetMapping("/users/delete")
    public String deleteUserById(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user );
        model.addAttribute("userRoles", roleService.getRoles());
        return "edit";
    }
    @PostMapping("/{id}")
    public String edit(@ModelAttribute("user") User updatedUser, @RequestParam("selectedRoles") String[] selectedRoles) {
        userService.saveUser(updatedUser, selectedRoles);
        return "redirect:/admin";
    }
//    @PatchMapping("/{id}")
//    public String getUpdateForm(@ModelAttribute("user") User user, @PathVariable("id") Long id,
//                                @RequestParam("rolesList") String[] selectedRoles) {
//        User newUser = userService.findById(id);
//        userService.updateUser(user, newUser, selectedRoles);
//        return "redirect:/admin";
//    }

}
