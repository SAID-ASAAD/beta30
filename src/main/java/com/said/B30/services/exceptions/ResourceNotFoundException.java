package com.said.B30.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object id){
        super("Recurso com Id " + id + " não encontrado." );
    }

}
