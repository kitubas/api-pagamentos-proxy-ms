package com.mentoria.apipagamentosproxyms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

@SpringBootTest
class PagamentoServiceImplTest {

	@Autowired
	PagamentoService pagamentoService;

	@Test
	void criarPagamentoComSucesso() {
		Pagamento pagamento = new Pagamento();
		pagamento.setValor(new BigDecimal(999999));
		pagamento.setContaOrigem("Joao");
		pagamento.setContaDestino("Santana");
		pagamento.setDataHora(LocalDateTime.now());

		var pagamentoCriado = pagamentoService.criarPagamento(pagamento);

		assertEquals(pagamento, pagamentoService.obterPagamento(pagamentoCriado.getId()));

	}

}