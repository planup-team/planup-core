package com.example.planupcore.domain.user.service;

import com.example.planupcore.domain.user.dto.*;
import com.example.planupcore.domain.user.entity.User;
import com.example.planupcore.domain.user.repository.UserRepository;
import com.example.planupcore.global.exception.ApiException;
import com.example.planupcore.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDetailDto createUser(UserCreateDto request) {
        var user = User.createUser(
            request.email(),
            request.nickname(),
            request.firstName(),
            request.lastName(),
            passwordEncoder.encode(request.password())
        );

        var savedUser = userRepository.save(user);
        return UserDetailDto.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserSummaryDto> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream()
            .map(UserSummaryDto::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserDetailDto getUserById(UUID id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public UserDetailDto updateUser(UUID id, UserUpdateDto request) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        var password = request.password();
        if (password == null
            || !passwordEncoder.matches(password, user.getHashedPassword())
        ) {
            throw new ApiException(ErrorCode.INVALID_PASSWORD);
        }

        var changed = applyChanges(
            user,
            request.email(),
            request.nickname(),
            request.firstName(),
            request.lastName()
        );

        var newPassword = request.newPassword();
        if (newPassword != null) {
            if (newPassword.isBlank()) {
                throw new ApiException(ErrorCode.INVALID_NEW_PASSWORD);
            }
            user.changePassword(passwordEncoder.encode(newPassword));

            changed = true;
        }

        if (!changed) {
            throw new ApiException(ErrorCode.NO_UPDATE_FIELD);
        }

        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public UserDetailDto updateUserByAdmin(UUID targetId, AdminUserUpdateDto request) {
        var user = userRepository.findById(targetId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        var changed = applyChanges(
            user,
            request.email(),
            request.nickname(),
            request.firstName(),
            request.lastName()
        );

        if (!changed) {
            throw new ApiException(ErrorCode.NO_UPDATE_FIELD);
        }

        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUserByAdmin(UUID targetId) {
        var user = userRepository.findById(targetId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    private boolean applyChanges(User user, String email, String nickname, String firstName, String lastName) {
        var changed = false;

        if (email != null) {
            user.changeEmail(email);
            changed = true;
        }

        if (nickname != null) {
            user.changeNickname(nickname);
            changed = true;
        }

        if (firstName != null) {
            user.changeFirstName(firstName);
            changed = true;
        }

        if (lastName != null) {
            user.changeLastName(lastName);
            changed = true;
        }

        return changed;
    }
}
