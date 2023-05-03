package com.mentoria.apipagamentosproxyms.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.EdicaoDeContaOrigemException;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;

@Service
public class PagamentoServiceImpl implements PagamentoService {

	@Autowired
	PagamentoRepository repository;

	@Autowired
	PagamentoMapper pagamentoMapper;
	@Override
	public Pagamento criarPagamento(PagamentoDTO pagamentoDTO) {
		Pagamento pagamento = pagamentoMapper.pagamentoDtoToModel(pagamentoDTO);
		pagamento.setDataHora(LocalDateTime.now().toString());
		pagamento.setExecutado(false);
		//TODO colocar msg na fila
		return repository.save(pagamento);
	}

	@Override
	public Pagamento obterPagamento(UUID uuid) {
		return repository.findById(uuid).orElseThrow();
	}

	@Override
	public void excluirPagamento(UUID id) {
		Pagamento pagamento = obterPagamento(id);
		if (pagamento.getExecutado()) {
			throw new PagamentoNaoPodeSerExcluidoException();
		}
		repository.deleteById(id);

	}

	@Override
	public Pagamento editarPagamento(Pagamento pagamentoEditado) {

			Pagamento pagamento = obterPagamento(pagamentoEditado.getId());
			if (pagamento.getExecutado()) {
				throw new PagamentoNaoPodeSerExcluidoException();
			}
			if (!pagamento.getContaOrigem().equals(pagamentoEditado.getContaOrigem()) ){
				throw new EdicaoDeContaOrigemException();
			}
			return repository.save(pagamentoEditado);



	}
}
