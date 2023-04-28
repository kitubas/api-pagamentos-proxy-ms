package com.mentoria.apipagamentosproxyms.service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagamentoServiceImpl implements PagamentoService{

    @Override
    public void criarPagamento(Pagamento pagamento) {
        System.out.println("execudando imp default");
    }

    @Override
    public Pagamento obterPagamento(UUID uuid) {
        return null;
    }
}
