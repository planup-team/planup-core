package com.example.planupcore.domain.user.dto;

import com.example.planupcore.domain.user.entity.User;

import java.util.UUID;

public record UserSummaryDto (
    UUID id,
    String email,
    String nickname
) {
    public static UserSummaryDto fromEntity(User user) {
        return new UserSummaryDto(
            user.getId(),
            user.getEmail(),
            user.getNickname()
        );
    }
}
