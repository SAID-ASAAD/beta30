package com.said.B30.businessrules.helpers.clientHelpers;

import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import com.said.B30.infrastructure.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientUpdate {

    public void updateClientData(ClientUpdateRequestDto dto, Client entity){
        if (dto.name() != null && !dto.name().isBlank()){
            entity.setName(dto.name());
        }
        if (dto.telephoneNumber() != null && !dto.telephoneNumber().isBlank()){
            entity.setTelephoneNumber(dto.telephoneNumber());
        }
        if (dto.email() != null && !dto.email().isBlank()){
            entity.setEmail(dto.email());
        }
        if (dto.note() != null && !dto.note().isBlank()){
            entity.setNote(dto.note());
        }
    }
}
