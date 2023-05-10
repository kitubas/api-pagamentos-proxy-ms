package com.mentoria.apipagamentosproxyms.service;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.model.Pagamento;

import java.util.UUID;

public interface PagamentoService {

	Pagamento criarPagamento(PagamentoDTO pagamento);

	Pagamento obterPagamento(UUID uuid);

	void excluirPagamento(UUID id);

	Pagamento editarPagamento(UUID id, PagamentoDTO pagamentoEditado);
}
