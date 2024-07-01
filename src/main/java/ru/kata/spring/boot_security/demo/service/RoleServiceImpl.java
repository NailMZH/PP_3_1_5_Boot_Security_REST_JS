package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Set<Role> getSetRoles(String role) {
        Set<Role> roles = new HashSet<>();
        switch (role) {
            case "ROLE_USER"-> {
                roles.add(new Role(2L, "ROLE_USER"));
            }
            case "ROLE_ADMIN" -> {
                roles.add(new Role(1L, "ROLE_ADMIN"));
            }
            case "ROLE_VIP" -> {
                roles.add(new Role(3L, "ROLE_VIP"));
            }
            case "ROLE_ADMIN,ROLE_USER" -> {
                roles.add(new Role(2L, "ROLE_USER"));
                roles.add(new Role(1L, "ROLE_ADMIN"));
            }
        }
        return roles;
    }

    @Override
    @Transactional
    public void addNewRole(Role role) {
        if (getByRole(role.getRole()) == null) {
            roleRepository.save(role);
        }
    }

    @Override
    @Transactional
    public Role getByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
