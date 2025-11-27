package com.said.B30.dtos.clientdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ClientUpdateDto(@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Não são permitidos caracteres especiais no nome de cliente.")
                              String name,
                              String telephoneNumber,
                              @Email(message = "Email inválido!")
                              String email,
                              String note) {
}
