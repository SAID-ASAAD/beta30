package com.said.B30.businessrules.helpers.userHelpers;

import com.said.B30.dtos.userdtos.UserUpdateDto;
import com.said.B30.infrastructure.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserUpdate {

    public void updateUserData(UserUpdateDto dto, User entity) {
        if (dto.name() != null && !dto.name().isBlank()) {
            entity.setName(dto.name());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            entity.setEmail(dto.email());
        }
        if (dto.password() != null && !dto.password().isBlank()) {
            entity.setPassword(dto.password());
        }
    }
}
