package com.said.B30.dtos.userdtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank(message = "O NOME de usuário deve ser informado para o cadastro de um novo usuário.")
                             @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Não são permitidos caracteres especiais no nome de usuário.")
                             String name,
                             @NotBlank(message = "O EMAIL deve ser informado para o cadastro de um novo usuário.")
                             @Email(message = "Email inválido!")
                             String email,
                             @NotBlank(message = "A SENHA deve ser informada para o cadastro de um novo usuário.")
                             @Size(min = 6, message = "A senha deve conter ao menos 6 caracteres")
                             String password) {
}
