package com.example.planupcore.domain.user.service;

import com.example.planupcore.domain.user.dto.UserCreateDto;
import com.example.planupcore.domain.user.dto.UserDetailDto;
import com.example.planupcore.domain.user.dto.UserSummaryDto;
import com.example.planupcore.domain.user.entity.User;
import com.example.planupcore.domain.user.repository.UserRepository;
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
        var user = User.create(
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
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public UserDetailDto updateUser(UUID id, UserCreateDto request) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        user.changeEmail(request.email());
        user.changeNickname(request.nickname());
        user.changeFirstName(request.firstName());
        user.changeLastName(request.lastName());
        user.changePassword(passwordEncoder.encode(request.password()));

        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
