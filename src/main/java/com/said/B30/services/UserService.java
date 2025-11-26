package com.said.B30.services;

import com.said.B30.dtos.userDtos.UserMapper;
import com.said.B30.dtos.userDtos.UserRequestDto;
import com.said.B30.dtos.userDtos.UserResponseDto;
import com.said.B30.dtos.userDtos.UserUpdate;
import com.said.B30.dtos.userDtos.UserUpdateDto;
import com.said.B30.infrastructure.repositories.UserRepository;
import com.said.B30.services.exceptions.DataEntryException;
import com.said.B30.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final UserUpdate userUpdate;

    public UserResponseDto createUser(UserRequestDto userRequest){
        try{
            return mapper.toResponse(userRepository.saveAndFlush(mapper.toEntity(userRequest)));
        }
        catch (DataIntegrityViolationException e){
            throw new DataEntryException("Certifique que o nome de usuário escolhido e o email informado não estão já cadastrados no sistema.");
        }
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        else {
            var user = userRepository.getReferenceById(id);
            user.setActive(false);
            userRepository.saveAndFlush(user);
        }
    }

    public UserResponseDto updateUserData(Long id, UserUpdateDto userUpdateDto) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        } else {
            try {
                var user = userRepository.getReferenceById(id);
                userUpdate.updateUserData(userUpdateDto, user);
                return mapper.toResponse(userRepository.saveAndFlush(user));
            } catch (DataIntegrityViolationException e) {
                throw new DataEntryException("Certifique que o nome de usuário escolhido e o email informado não estão já cadastrados no sistema.");
            }
        }
    }
}
