package com.said.B30.dtos.userDtos;

import com.said.B30.infrastructure.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdate {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateUserData(UserRequestDto requestDto, @MappingTarget User userEntity);
}
