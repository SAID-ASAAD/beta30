package com.said.B30.dtos.clientdtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequestDto(@NotBlank(message = "O NOME deve ser informado para o cadastro de um novo cliente.")
                               @Pattern(regexp = "^[\\p{L}\\s]*$", message = "Não são permitidos caracteres especiais no nome de cliente.")
                               String name,
                               @NotBlank(message = "O TELEFONE do cliente deve ser informado para o cadastro de um novo cliente.")
                               String telephoneNumber,
                               @Email(message = "Email inválido!")
                               String email,
                               String note) {
}
