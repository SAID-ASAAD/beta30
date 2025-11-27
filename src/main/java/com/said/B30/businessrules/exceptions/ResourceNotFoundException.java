package com.said.B30.businessrules.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object id){
        super("Recurso com Id " + id + " não encontrado." );
    }

}
