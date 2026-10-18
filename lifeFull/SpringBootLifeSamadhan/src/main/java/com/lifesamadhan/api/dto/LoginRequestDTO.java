package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
public class LoginRequestDTO {

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
}