package com.example.planupcore.domain.user.dto;

import com.example.planupcore.domain.user.entity.User;

import java.util.UUID;

public record UserDetailDto (
    UUID id,
    String email,
    String nickname,
    String firstName,
    String lastName
) {
    public static UserDetailDto fromEntity(User user) {
        return new UserDetailDto(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}
