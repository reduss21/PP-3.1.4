package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="roles_bs")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public void setRole(String role) {
        this.role = role;
    }

    private String role;


    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
