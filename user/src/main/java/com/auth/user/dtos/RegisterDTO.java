package com.auth.user.dtos;

import com.auth.user.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterDTO {

    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotBlank
    String email;
    RoleEnum role;
}
