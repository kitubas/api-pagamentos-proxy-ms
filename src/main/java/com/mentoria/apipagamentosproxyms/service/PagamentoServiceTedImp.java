package com.mentoria.apipagamentosproxyms.service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

import java.util.UUID;

public class PagamentoServiceTedImp implements PagamentoService{
    @Override
    public void criarPagamento(Pagamento pagamento) {
        System.out.println("executando imp ted");
    }

    @Override
    public Pagamento obterPagamento(UUID uuid) {
        return null;
    }
}
