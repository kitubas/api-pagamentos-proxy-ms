package com.mentoria.apipagamentosproxyms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class PagamentoNaoPodeSerExcluidoException extends RuntimeException {

    public PagamentoNaoPodeSerExcluidoException() {
        super("Pagamento já foi executado. Não pode ser deletado.");
    }
}
