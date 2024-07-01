package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getRoles();
    Set<Role> getSetRoles(String role);
    void addNewRole(Role role);
    Role getByRole(String role);
}
