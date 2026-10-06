package io.nology.project.auth.entity;

import io.nology.project.auth.Role;
import io.nology.project.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="app_users")
public class AppUser extends BaseEntity {
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public AppUser(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
