package com.security.userModel;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role implements Serializable {
    @Id
    private Long id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
    joinColumns = @JoinColumn(name = "role_name"),
    inverseJoinColumns = @JoinColumn(name = "username"))
    private Set<User> users;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
