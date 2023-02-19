package com.company.users;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Table(name = "users")
@Component
@Entity
public class Users {
    @Id
    private String email;
    private String password;
    private String role;

    public Users() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

