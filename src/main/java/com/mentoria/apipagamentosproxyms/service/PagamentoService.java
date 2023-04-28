package com.mentoria.apipagamentosproxyms.service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

import java.util.UUID;

public interface PagamentoService {


    void criarPagamento(Pagamento pagamento);

    Pagamento obterPagamento(UUID uuid);
}
