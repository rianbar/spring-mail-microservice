package com.auth.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank String username, @NotBlank String password) {
}
