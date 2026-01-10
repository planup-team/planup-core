package com.example.planupcore.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDto (
    @Email(message = "Email should be valid")
    String email,

    @Size(min = 3, max = 50, message = "Nickname must be between 3 and 50 characters")
    String nickname,

    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    String firstName,

    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    String lastName,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password,

    @Size(min = 8, message = "Password must be at least 8 characters long")
    String newPassword
) {}
