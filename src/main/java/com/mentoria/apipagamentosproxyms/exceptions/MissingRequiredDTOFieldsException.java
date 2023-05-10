package com.mentoria.apipagamentosproxyms.exceptions;

public class MissingRequiredDTOFieldsException extends RuntimeException{

    public MissingRequiredDTOFieldsException() {
        super("DTO missing required fields");
    }
}
