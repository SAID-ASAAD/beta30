package com.said.B30.services;

import com.said.B30.dtos.UserMapper;
import com.said.B30.dtos.UserRequestDto;
import com.said.B30.dtos.UserResponseDto;
import com.said.B30.dtos.UserUpdate;
import com.said.B30.infrastructure.entities.User;
import com.said.B30.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final UserUpdate userUpdate;

    public UserResponseDto createUser(UserRequestDto userRequest){
        return mapper.toResponse(userRepository.saveAndFlush(mapper.toEntity(userRequest)));
    }

    public void deleteUser(Long id){
        User user = userRepository.getReferenceById(id);
        user.setActive(false);
        userRepository.saveAndFlush(user);
    }

    public UserResponseDto updateUserData(Long id, UserRequestDto userRequest){
        User user = userRepository.getReferenceById(id);
        userUpdate.updateUserData(userRequest, user);
        return mapper.toResponse(userRepository.saveAndFlush(user));
    }
}
