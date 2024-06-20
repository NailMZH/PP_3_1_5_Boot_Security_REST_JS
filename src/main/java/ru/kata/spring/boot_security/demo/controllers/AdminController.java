package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//    @PostMapping("/saveUser")
//    public String saveUser(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("roles", roleServiceImpl.allRoles());
//        userServiceImpl.saveUser(user);
//        return "redirect:/admin_4";
//    }
//    @PostMapping("/saveUser")
//    public String saveUser(@ModelAttribute("user") User user,
//                           @RequestParam(required = false) String roleAdmin,
//                           @RequestParam(required = false) String roleVip) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleServiceImpl.getRoleByName("ROLE_USER"));
//        if(roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
//            roles.add(roleServiceImpl.getRoleByName("ROLE_ADMIN"));
//        }
//        if(roleVip != null && roleVip.equals("ROLE_VIP")) {
//            roles.add(roleServiceImpl.getRoleByName("ROLE_VIP"));
//        }
//        user.setRoles(roles);
//        userServiceImpl.saveUser(user);
//        return "redirect:/admin_4";
//    }
}
