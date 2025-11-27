package com.said.B30.dtos.userdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Não são permitidos caracteres especiais no nome de usuário.")
                            String name,
                            @Email(message = "Email inválido!")
                            String email,
                            @Size(min = 6, message = "A senha deve conter ao menos 6 caracteres")
                            String password) {
}
