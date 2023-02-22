package com.company.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterDto {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
