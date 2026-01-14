package com.said.B30.controllers.exceptions;

import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.services.ClientService;
import com.said.B30.controllers.ClientController;
import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = ClientController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ViewExceptionHandler {

    private final ClientService clientService;

    @ExceptionHandler(DeletionNotAllowedException.class)
    public ModelAndView deletionNotAllowed(DeletionNotAllowedException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        Long id = Long.parseLong(idStr);

        ClientResponseDto client = clientService.findClientById(id);
        
        ModelAndView mv = new ModelAndView("clients/clientdetails");
        mv.addObject("client", client);
        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }

    @ExceptionHandler(DataEntryException.class)
    public ModelAndView dataEntryError(DataEntryException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        ModelAndView mv;

        if (path.contains("/register")) {
            mv = new ModelAndView("clients/clientregisterform");
            ClientRequestDto dto = new ClientRequestDto(
                request.getParameter("name"),
                request.getParameter("telephoneNumber"),
                request.getParameter("email"),
                request.getParameter("note")
            );
            mv.addObject("clientRequestDto", dto);
        } else {
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long id = Long.parseLong(idStr);
            
            mv = new ModelAndView("clients/clientupdateform");
            ClientResponseDto clientOriginal = clientService.findClientById(id);
            
            mv.addObject("client", clientOriginal);
            mv.addObject("clientUpdateRequestDto", new ClientUpdateRequestDto(
                request.getParameter("name"),
                request.getParameter("telephoneNumber"),
                request.getParameter("email"),
                request.getParameter("note")
            ));
        }

        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView resourceNotFound(ResourceNotFoundException e) {
        ModelAndView mv = new ModelAndView("clients/clients");
        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }
}
