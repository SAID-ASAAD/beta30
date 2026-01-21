package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.clienthelpers.ClientMapper;
import com.said.B30.businessrules.helpers.clienthelpers.ClientUpdate;
import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import com.said.B30.dtos.clientdtos.ClientUpdateResponseDto;
import com.said.B30.infrastructure.entities.Client;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ClientResponseDto> findClientsByNameContaining(String name){
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClientResponseDto findClientById(Long id){
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public ClientUpdateRequestDto getClientForUpdate(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toUpdateRequest(mapper.toResponse(client));
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

    public Page<ClientResponseDto> findAllClientsPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public ClientUpdateResponseDto updateClientData(Long id, ClientUpdateRequestDto clientUpdateRequestDto){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }else{
            try {
                var client = repository.getReferenceById(id);
                clientUpdate.updateClientData(clientUpdateRequestDto, client);
                return mapper.toUpdateResponseDto(repository.saveAndFlush(client));
            }
            catch (DataIntegrityViolationException e){
                throw new DataEntryException("Certifique que o TELEFONE informado não está já cadastrdo em outro cliente no sistema");
            }
        }
    }

    public void deleteClientById(Long id){
        var client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if(!client.getOrders().isEmpty() || !client.getClientSales().isEmpty()){
            throw new DeletionNotAllowedException("Não é possível deletar um cliente com pedidos ou vendas registrados em seu nome");
        } else {
            repository.deleteById(id);
        }
    }
}
