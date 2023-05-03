package com.mentoria.apipagamentosproxyms.exceptions;

public class PagamentoNaoPodeSerExcluidoException extends RuntimeException {

    public PagamentoNaoPodeSerExcluidoException() {
        super("Pagamento já foi executado. Não pode ser deletado.");
    }
}
