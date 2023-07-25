package com.auth.user.dtos;

import com.auth.user.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private RoleEnum role;
}
