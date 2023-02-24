package com.company.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterDto {
    private static String username;
    private String password;

    public static String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
