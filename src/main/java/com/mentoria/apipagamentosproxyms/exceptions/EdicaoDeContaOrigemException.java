package com.mentoria.apipagamentosproxyms.exceptions;

public class EdicaoDeContaOrigemException extends RuntimeException {


    public EdicaoDeContaOrigemException() {
        super("A conta de origem não pode ser editada");
    }
}
