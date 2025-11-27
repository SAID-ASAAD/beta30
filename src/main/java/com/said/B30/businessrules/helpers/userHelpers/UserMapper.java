package com.said.B30.businessrules.helpers.userHelpers;

import com.said.B30.dtos.userdtos.UserRequestDto;
import com.said.B30.dtos.userdtos.UserResponseDto;
import com.said.B30.infrastructure.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    User toEntity(UserRequestDto userRequest);
    UserResponseDto toResponse(User userEntity);

}
