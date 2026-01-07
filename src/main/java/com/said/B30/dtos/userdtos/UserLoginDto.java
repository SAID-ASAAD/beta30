package com.said.B30.dtos.userdtos;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(@NotBlank(message = "O NOME deve ser informado para o login.")
                        String name,
                           @NotBlank(message = "A SENHA deve ser informada para o login.")
                        String password) {
}
