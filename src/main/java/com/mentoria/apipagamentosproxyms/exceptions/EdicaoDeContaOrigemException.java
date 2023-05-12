package com.mentoria.apipagamentosproxyms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EdicaoDeContaOrigemException extends RuntimeException {


    public EdicaoDeContaOrigemException() {
        super("A conta de origem não pode ser editada");
    }
}
