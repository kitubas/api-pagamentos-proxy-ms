package com.mentoria.apipagamentosproxyms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest//(classes = PagamentoServiceImpl.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PagamentoServiceImplTest {

	@InjectMocks
	PagamentoServiceImpl pagamentoService;

	@Mock
	PagamentoRepository pagamentoRepository;

	@Mock
	PagamentoMapper pagamentoMapper;
	PagamentoDTO pagamentoDTO;
	void dadoUmPagamentoDTO(){
		pagamentoDTO = new PagamentoDTO();
		pagamentoDTO.setValor(new BigDecimal(999999));
		pagamentoDTO.setContaOrigem("Joao");
		pagamentoDTO.setContaDestino("Santana");
	}
	@Test
	void criarPagamentoComSucesso() {

		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		whenGettingTheEntity();

		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);
		Pagamento pagamentoResgatado = pagamentoService.obterPagamento(pagamentoCriado.getId());

		assertTrue(pagamentoDTO.getValor().compareTo(pagamentoResgatado.getValor())==0);
		assertEquals(pagamentoDTO.getContaDestino(),pagamentoResgatado.getContaDestino());
		assertEquals(pagamentoDTO.getContaOrigem(),pagamentoResgatado.getContaOrigem());
		assertFalse(pagamentoResgatado.getExecutado());
	}

	private void whenGettingTheEntity() {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(LocalDateTime.now().toString())
				.id(UUID.randomUUID())
				.valor(new BigDecimal(99999))
				.contaOrigem(pagamentoDTO.getContaOrigem())
				.contaDestino(pagamentoDTO.getContaDestino())
				.executado(false)
				.build()));
	}

	private void whenSavingTheEntity() {
		when(pagamentoRepository.save(Mockito.any())).thenReturn(Pagamento.builder()
				.dataHora(LocalDateTime.now().toString())
				.id(UUID.randomUUID())
				.valor(new BigDecimal(99999))
				.contaOrigem(pagamentoDTO.getContaOrigem())
				.contaDestino(pagamentoDTO.getContaDestino())
				.executado(false)
				.build());

	}


	@Test
	void excluirPagamentoComSucesso(){
		dadoUmPagamentoDTO();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		pagamentoService.excluirPagamento(pagamentoCriado.getId());

		assertThrows(NoSuchElementException.class,() -> pagamentoService.obterPagamento(pagamentoCriado.getId())) ;



	}

	@Test
	void editarPagamentoComSucesso(){
		dadoUmPagamentoDTO();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		Pagamento pagamentoEditado = pagamentoCriado;
		pagamentoEditado.setContaDestino("oxe");
		pagamentoEditado.setValor(new BigDecimal(8888));

		Pagamento retornoPagamentoEditado = pagamentoService.editarPagamento(pagamentoEditado);

		assertEquals(pagamentoEditado.getContaOrigem(),retornoPagamentoEditado.getContaOrigem());
		assertEquals(pagamentoEditado.getValor(),retornoPagamentoEditado.getValor());
		assertEquals(pagamentoEditado.getContaDestino(),retornoPagamentoEditado.getContaDestino());
		assertEquals(pagamentoEditado.getId(), retornoPagamentoEditado.getId());
		assertFalse(retornoPagamentoEditado.getExecutado());

		Pagamento pagamento = pagamentoService.obterPagamento(pagamentoCriado.getId());
		assertTrue(pagamentoEditado.equals(pagamento));

		//assertEquals(pagamentoEditado,pagamentoService.obterPagamento(pagamentoCriado.getId()));

	}

	@Test//TODO
	void exluirPagamentoJaExecutado(){

		dadoUmPagamentoExecutado();

		dadoUmPagamentoDTO();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);
//
//		pagamentoService.excluirPagamento(pagamentoCriado.getId());
//
//		assertThrows(NoSuchElementException.class,() -> pagamentoService.obterPagamento(pagamentoCriado.getId())) ;
	}

	private void dadoUmPagamentoExecutado() {

	//when(pagamentoRepository.save())


	}

}