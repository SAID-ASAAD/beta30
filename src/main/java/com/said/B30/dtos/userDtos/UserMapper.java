package com.said.B30.dtos.userDtos;

import com.said.B30.infrastructure.entities.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    User toEntity(UserRequestDto userRequest);
    UserResponseDto toResponse(User userEntity);

}
