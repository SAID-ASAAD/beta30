package com.said.B30.controllers;

import com.said.B30.dtos.clientDtos.ClientRequestDto;
import com.said.B30.dtos.clientDtos.ClientResponseDto;
import com.said.B30.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto clientRequest){
        var obj = clientService.createClient(clientRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findClientById(@PathVariable Long id){
        return ResponseEntity.ok().body(clientService.findClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> findAllClients(){
        return ResponseEntity.ok().body(clientService.findAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClientData(@PathVariable Long id, @RequestBody ClientRequestDto clientRequest){
        return ResponseEntity.ok(clientService.updateClientData(id, clientRequest));
    }
}
