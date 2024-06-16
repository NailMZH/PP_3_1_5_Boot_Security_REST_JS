package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "t_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, message = "Не меньше 5 знаков")
    private String username;

    @Size(min = 2, message = "Не меньше 5 знаков")
    private String password;

    @Transient
    private String passwordConfig;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column
    private String email;

    public User() {
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPasswordConfig(String passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    public String getPasswordConfig() {
        return passwordConfig;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return getPassword();
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
