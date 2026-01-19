package com.said.B30.controllers;

import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.businessrules.services.ClientService;
import com.said.B30.businessrules.services.OrderService;
import com.said.B30.businessrules.services.ProductService;
import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import com.said.B30.dtos.clientdtos.ClientUpdateResponseDto;
import com.said.B30.dtos.orderdtos.OrderResponseDto;
import com.said.B30.dtos.productdtos.ProductFullResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/register")
    public ModelAndView getRegisterClientPage(){
        ModelAndView mv = new ModelAndView("clients/clientregisterform");
        mv.addObject("clientRequestDto", new ClientRequestDto(null, null, null, null));
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView createClient(@Valid ClientRequestDto clientRequest, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("clients/clientregisterform");
        }
        clientService.createClient(clientRequest);
        return new ModelAndView("redirect:/clients");
    }

    @GetMapping
    public ModelAndView getClientsPage(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<ClientResponseDto> clientPage = clientService.findAllClientsPaginated(pageable);
        
        ModelAndView mv = new ModelAndView("clients/clients");
        mv.addObject("clients", clientPage);
        return mv;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<ClientResponseDto> searchClients(@RequestParam String name) {
        return clientService.findClientsByNameContaining(name);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditClientForm(@PathVariable Long id) {
        ClientResponseDto client = clientService.findClientById(id);
        ModelAndView mv = new ModelAndView("clients/clientupdateform");
        mv.addObject("client", client);
        // Usa o método do service para obter o DTO pronto para edição
        mv.addObject("clientUpdateRequestDto", clientService.getClientForUpdate(id));
        return mv;
    }

    @PutMapping("/edit/{id}")
    public ModelAndView updateClientData(@PathVariable Long id, @Valid ClientUpdateRequestDto clientUpdate, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("clients/clientupdateform");
            mv.addObject("client", new ClientResponseDto(id, clientUpdate.name(), clientUpdate.telephoneNumber(), clientUpdate.email(), clientUpdate.note()));
            return mv;
        }
        clientService.updateClientData(id, clientUpdate);
        return new ModelAndView("redirect:/clients/details/{id}");
    }

    @GetMapping("/details/{id}")
    public ModelAndView getClientDetailsPage(@PathVariable Long id) {
        ClientResponseDto client = clientService.findClientById(id);
        List<OrderResponseDto> orders = orderService.findOrdersByClientId(id);
        List<ProductFullResponseDto> products = productService.findProductsByClientId(id);
        
        ModelAndView mv = new ModelAndView("clients/clientdetails");
        mv.addObject("client", client);
        mv.addObject("orders", orders);
        mv.addObject("products", products);
        return mv;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return new ModelAndView("redirect:/clients");
    }

    // Métodos da API REST
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findClientById(@PathVariable Long id){
        return ResponseEntity.ok().body(clientService.findClientById(id));
    }

    @GetMapping("/list")
    public ModelAndView findAllClients(){
        List<ClientResponseDto> clients = clientService.findAllClients();
        ModelAndView mv = new ModelAndView("clients/clientlist");
        mv.addObject("clients", clients);
        return mv;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientUpdateResponseDto> updateClientData(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequestDto clientUpdate){
        return ResponseEntity.ok(clientService.updateClientData(id, clientUpdate));
    }
}
