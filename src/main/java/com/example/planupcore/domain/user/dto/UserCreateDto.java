package com.example.planupcore.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(

    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Nickname is required")
    String nickname,

    @NotBlank(message = "First name is required")
    String firstName,

    @NotBlank(message = "Last name is required")
    String lastName,

    @NotBlank(message = "Password is required")
    String password
) {}
