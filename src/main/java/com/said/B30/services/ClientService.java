package com.said.B30.services;

import com.said.B30.dtos.clientDtos.CilentUpdate;
import com.said.B30.dtos.clientDtos.ClientMapper;
import com.said.B30.dtos.clientDtos.ClientRequestDto;
import com.said.B30.dtos.clientDtos.ClientResponseDto;
import com.said.B30.infrastructure.entities.Client;
import com.said.B30.infrastructure.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final CilentUpdate cilentUpdate;

    public ClientResponseDto createClient(ClientRequestDto clientRequest){
        return mapper.toResponse(repository.saveAndFlush(mapper.toEntity(clientRequest)));
    }

    public ClientResponseDto findClientById(Long id){
        return mapper.toResponse(repository.findById(id).orElseThrow());
    }

    public List<ClientResponseDto> findAllClients(){
        List<Client> clientEntities = repository.findAll();
        List<ClientResponseDto> clientResponses = new ArrayList<>();
        for (Client c : clientEntities){ //ver como usar o stream() aqui
           var clientResponse = mapper.toResponse(c);
            clientResponses.add(clientResponse);
        }
        return clientResponses;
    }

    public ClientResponseDto updateClientData(Long id, ClientRequestDto clientRequest){
        var clientEntity = repository.getReferenceById(id);
        cilentUpdate.updateClientData(clientRequest, clientEntity);
        return mapper.toResponse(repository.saveAndFlush(clientEntity));
    }
}
