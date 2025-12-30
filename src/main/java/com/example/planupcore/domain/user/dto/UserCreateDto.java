package com.example.planupcore.domain.user.dto;

public record UserCreateDto(
    String email,
    String nickname,
    String firstName,
    String lastName,
    String password
) {}
