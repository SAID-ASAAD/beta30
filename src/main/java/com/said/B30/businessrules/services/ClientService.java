package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.clientHelpers.ClientMapper;
import com.said.B30.businessrules.helpers.clientHelpers.ClientUpdate;
import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.dtos.clientdtos.ClientUpdateDto;
import com.said.B30.infrastructure.entities.Client;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final ClientUpdate clientUpdate;

    public ClientResponseDto createClient(ClientRequestDto clientRequest){
        try{
            return mapper.toResponse(repository.saveAndFlush(mapper.toEntity(clientRequest)));
        }
        catch (DataIntegrityViolationException e){
            throw new DataEntryException("Certifique que o TELEFONE informado não está já cadastrado no sistema.");
        }
    }

    public ClientResponseDto findClientById(Long id){
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public List<ClientResponseDto> findAllClients(){
        List<Client> clientEntities = repository.findAll();
        List<ClientResponseDto> clientResponses = new ArrayList<>();
        for (Client c : clientEntities){
           var clientResponse = mapper.toResponse(c);
            clientResponses.add(clientResponse);
        }
        return clientResponses;
    }

    public ClientUpdateDto updateClientData(Long id, ClientUpdateDto clientUpdateDto){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }else{
            try {
                var client = repository.getReferenceById(id);
                clientUpdate.updateClientData(clientUpdateDto, client);
                return mapper.toUpdateDto(repository.saveAndFlush(client));
            }
            catch (DataIntegrityViolationException e){
                throw new DataEntryException("Certifique que o TELEFONE informado não está já cadastrdo em outro cliente no sistema");
            }
        }
    }
}
