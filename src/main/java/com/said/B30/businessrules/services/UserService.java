package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.userhelpers.UserMapper;
import com.said.B30.dtos.userdtos.UserRequestDto;
import com.said.B30.dtos.userdtos.UserResponseDto;
import com.said.B30.businessrules.helpers.userhelpers.UserUpdate;
import com.said.B30.dtos.userdtos.UserUpdateDto;
import com.said.B30.infrastructure.repositories.UserRepository;
import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
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
            throw new DataEntryException("Certifique que o nome de usuário escolhido e o email informado não estão já cadastrados em outro usuário do sistema.");
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
                throw new DataEntryException("Certifique que o nome de usuário e o email informados para atualização não estão já cadastrados em outro usuário do sistema.");
            }
        }
    }
}
