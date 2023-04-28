package com.mentoria.apipagamentosproxyms.service;

import com.mentoria.apipagamentosproxyms.config.PagamentoTestConfig;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Import(PagamentoTestConfig.class)
class PagamentoServiceImplTest {

    @Autowired
    PagamentoService pagamentoService;

    @Test
    void criarPagamentoComSucesso(){
        Pagamento pagamento = new Pagamento();
        UUID uuid = UUID.randomUUID();
        pagamento.setId(uuid);
        pagamento.setValor(new BigDecimal(999999));
        pagamento.setContaOrigem("Joao");
        pagamento.setContaDestino("Santana");
        pagamento.setDataHora(LocalDateTime.now());

        pagamentoService.criarPagamento(pagamento);

        assertEquals(pagamento,pagamentoService.obterPagamento(uuid));


    }

}