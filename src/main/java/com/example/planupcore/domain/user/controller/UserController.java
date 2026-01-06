package com.example.planupcore.domain.user.controller;

import com.example.planupcore.domain.user.dto.UserCreateDto;
import com.example.planupcore.domain.user.dto.UserDetailDto;
import com.example.planupcore.domain.user.dto.UserSummaryDto;
import com.example.planupcore.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDetailDto> createUser(
        @RequestBody @Valid UserCreateDto request
    ) {
        var user = userService.createUser(request);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUserById(
        @PathVariable UUID id
    ) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> updateUser(
        @PathVariable UUID id,
        @RequestBody @Valid UserCreateDto request
    ) {
        var updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable UUID id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
