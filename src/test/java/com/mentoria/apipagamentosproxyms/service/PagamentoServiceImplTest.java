package com.mentoria.apipagamentosproxyms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.EdicaoDeContaOrigemException;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PagamentoServiceImplTest {



	PagamentoRepository pagamentoRepository = Mockito.mock(PagamentoRepository.class);

	PagamentoMapper pagamentoMapper = new PagamentoMapper();

	PagamentoServiceImpl pagamentoService = new PagamentoServiceImpl(pagamentoRepository,pagamentoMapper);
	PagamentoDTO pagamentoDTO;
	void dadoUmPagamentoDTO(){
		pagamentoDTO = new PagamentoDTO(new BigDecimal(99999),"Joao","Santana");

	}
	@Test
	void criarPagamentoComSucesso() {

		dadoUmPagamentoDTO();
		whenSavingTheEntity();

		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);
		whenGettingANonExecutedTheEntity(pagamentoCriado);
		Pagamento pagamentoResgatado = pagamentoService.obterPagamento(pagamentoCriado.getId());

		assertEquals(0, pagamentoDTO.valor().compareTo(pagamentoResgatado.getValor()));
		assertEquals(pagamentoDTO.contaDestino(),pagamentoResgatado.getContaDestino());
		assertEquals(pagamentoDTO.contaOrigem(),pagamentoResgatado.getContaOrigem());
		assertFalse(pagamentoResgatado.getExecutado());
		assertEquals(pagamentoCriado.getDataHora(),pagamentoResgatado.getDataHora());
		assertEquals(pagamentoCriado.getId(),pagamentoResgatado.getId());
	}



	@Test
	void excluirPagamentoComSucesso(){
		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		whenDeletingTheEntity();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		whenGettingANonExecutedTheEntity(pagamentoCriado);
		pagamentoService.excluirPagamento(pagamentoCriado.getId());

		whenGettingANonExistentEntity();
		assertThrows(NoSuchElementException.class,() -> pagamentoService.obterPagamento(pagamentoCriado.getId())) ;



	}

	@Test
	void editarPagamentoComSucesso(){
		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		PagamentoDTO pagamentoEditado =new PagamentoDTO(new BigDecimal(8888),pagamentoCriado.getContaOrigem(),"oxe");

		whenEditingTheEntity(pagamentoCriado, pagamentoEditado);
		Pagamento retornoPagamentoEditado = pagamentoService.editarPagamento(pagamentoCriado.getId(), pagamentoEditado);

		assertEquals(pagamentoEditado.contaOrigem(),retornoPagamentoEditado.getContaOrigem());
		assertEquals(pagamentoEditado.valor(),retornoPagamentoEditado.getValor());
		assertEquals(pagamentoEditado.contaDestino(),retornoPagamentoEditado.getContaDestino());
		assertEquals(pagamentoCriado.getId(), retornoPagamentoEditado.getId());
		assertFalse(retornoPagamentoEditado.getExecutado());


	}

	@Test
	void exluirPagamentoJaExecutado(){

		givenAnExecutedEntity();
		assertThrows(PagamentoNaoPodeSerExcluidoException.class,() -> pagamentoService.excluirPagamento(UUID.randomUUID())) ;
	}

	@Test
	void editarUmPagamentoJaExecutado(){
		dadoUmPagamentoDTO();

		givenAnExecutedEntity();
		assertThrows(PagamentoNaoPodeSerExcluidoException.class,() -> pagamentoService.editarPagamento(UUID.randomUUID(), pagamentoDTO)) ;
	}


	@Test
	void editarContaDeOrigemDoPagamento(){
		dadoUmPagamentoDTO();

		whenGettingAnEntityWithDifferentContaOrigem();
		assertThrows(EdicaoDeContaOrigemException.class,() -> pagamentoService.editarPagamento(UUID.randomUUID(),pagamentoDTO)) ;
	}

	private void whenGettingAnEntityWithDifferentContaOrigem() {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(LocalDateTime.now().toString())
				.id(UUID.randomUUID())
				.valor(new BigDecimal(99999))
				.contaOrigem("oxe")
				.contaDestino(pagamentoDTO.contaDestino())
				.executado(false)
				.build()));
	}

	private void whenGettingANonExecutedTheEntity(Pagamento pagamentoCriado) {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(pagamentoCriado.getDataHora())
				.id(pagamentoCriado.getId())
				.valor(pagamentoCriado.getValor())
				.contaOrigem(pagamentoCriado.getContaOrigem())
				.contaDestino(pagamentoDTO.contaDestino())
				.executado(false)
				.build()));
	}
	private void whenSavingTheEntity() {
		when(pagamentoRepository.save(Mockito.any())).thenReturn(Pagamento.builder()
				.dataHora(LocalDateTime.now().toString())
				.id(UUID.randomUUID())
				.valor(new BigDecimal(99999))
				.contaOrigem(pagamentoDTO.contaOrigem())
				.contaDestino(pagamentoDTO.contaDestino())
				.executado(false)
				.build());

	}




	private void whenGettingANonExistentEntity() {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.empty());
	}

	private void whenDeletingTheEntity() {
		doNothing().when(pagamentoRepository).deleteById(Mockito.any());

	}


	private void whenEditingTheEntity(Pagamento pagamentoCriado, PagamentoDTO editado) {
		when(pagamentoRepository.save(Mockito.any())).thenReturn(Pagamento.builder()
				.dataHora(pagamentoCriado.getDataHora())
				.id(pagamentoCriado.getId())
				.valor(editado.valor())
				.contaOrigem(editado.contaOrigem())
				.contaDestino(editado.contaDestino())
				.executado(false)
				.build());

		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(pagamentoCriado));
	}



	private void givenAnExecutedEntity() {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(LocalDateTime.now().toString())
				.id(UUID.randomUUID())
				.valor(new BigDecimal(88888))
				.contaOrigem("oxe")
				.contaDestino("santana")
				.executado(true)
				.build()));


	}

}