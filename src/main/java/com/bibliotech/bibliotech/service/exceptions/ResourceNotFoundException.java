package com.bibliotech.bibliotech.service.exceptions;

import org.aspectj.apache.bcel.classfile.Code;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super("Recurso não encontrado.");
    }

    public ResourceNotFoundException(Object id) {
        super("Recurso não encontrado. Id: " + id);
    }

}
