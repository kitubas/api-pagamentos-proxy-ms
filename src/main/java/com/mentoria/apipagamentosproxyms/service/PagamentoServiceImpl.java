package com.mentoria.apipagamentosproxyms.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;

@Service
public class PagamentoServiceImpl implements PagamentoService {

	@Autowired
	PagamentoRepository repository;

	@Override
	public Pagamento criarPagamento(Pagamento pagamento) {
		return repository.save(pagamento);
	}

	@Override
	public Pagamento obterPagamento(UUID uuid) {
		return repository.findById(uuid).orElseThrow();
	}
}
