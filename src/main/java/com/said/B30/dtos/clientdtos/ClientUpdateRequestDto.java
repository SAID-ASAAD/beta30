package com.said.B30.dtos.clientdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ClientUpdateRequestDto(
                                     @Pattern(regexp = "^[\\p{L}\\s]*$", message = "Não são permitidos caracteres especiais no nome de cliente.")
                                     String name,
                                     String telephoneNumber,
                                     @Email(message = "Email inválido!")
                                     String email,
                                     String note) {
}
