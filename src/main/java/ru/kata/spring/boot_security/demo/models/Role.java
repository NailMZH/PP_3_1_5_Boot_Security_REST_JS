package ru.kata.spring.boot_security.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    List<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(String authority) {
        super();
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && authority.equals(role.authority) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, users);
    }

}
