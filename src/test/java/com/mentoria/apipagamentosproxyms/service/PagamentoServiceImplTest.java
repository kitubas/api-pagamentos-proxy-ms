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

@SpringBootTest//(classes = PagamentoServiceImpl.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PagamentoServiceImplTest {



	PagamentoRepository pagamentoRepository = Mockito.mock(PagamentoRepository.class);

	PagamentoMapper pagamentoMapper = new PagamentoMapper();

	PagamentoServiceImpl pagamentoService = new PagamentoServiceImpl(pagamentoRepository,pagamentoMapper);
	PagamentoDTO pagamentoDTO;
	void dadoUmPagamentoDTO(){
		pagamentoDTO = new PagamentoDTO();
		pagamentoDTO.setValor(new BigDecimal(99999));
		pagamentoDTO.setContaOrigem("Joao");
		pagamentoDTO.setContaDestino("Santana");
	}
	@Test
	void criarPagamentoComSucesso() {

		dadoUmPagamentoDTO();
		whenSavingTheEntity();

		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);
		whenGettingANonExecutedTheEntity(pagamentoCriado);
		Pagamento pagamentoResgatado = pagamentoService.obterPagamento(pagamentoCriado.getId());

		assertTrue(pagamentoDTO.getValor().compareTo(pagamentoResgatado.getValor())==0);
		assertEquals(pagamentoDTO.getContaDestino(),pagamentoResgatado.getContaDestino());
		assertEquals(pagamentoDTO.getContaOrigem(),pagamentoResgatado.getContaOrigem());
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

		Pagamento pagamentoEditado = pagamentoCriado;
		pagamentoEditado.setContaDestino("oxe");
		pagamentoEditado.setValor(new BigDecimal(8888));

//		whenGettingANonExecutedTheEntity(pagamentoEditado);
		whenEditingTheEntity(pagamentoEditado);
		Pagamento retornoPagamentoEditado = pagamentoService.editarPagamento(pagamentoEditado);

		assertEquals(pagamentoEditado.getContaOrigem(),retornoPagamentoEditado.getContaOrigem());
		assertEquals(pagamentoEditado.getValor(),retornoPagamentoEditado.getValor());
		assertEquals(pagamentoEditado.getContaDestino(),retornoPagamentoEditado.getContaDestino());
		assertEquals(pagamentoEditado.getId(), retornoPagamentoEditado.getId());
		assertFalse(retornoPagamentoEditado.getExecutado());

		assertTrue(pagamentoEditado.equals(retornoPagamentoEditado));

		//assertEquals(pagamentoEditado,pagamentoService.obterPagamento(pagamentoCriado.getId()));

	}

	@Test
	void exluirPagamentoJaExecutado(){

		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		givenAnExecutedEntity(pagamentoCriado);
		assertThrows(PagamentoNaoPodeSerExcluidoException.class,() -> pagamentoService.excluirPagamento(pagamentoCriado.getId())) ;
	}

	@Test
	void editarUmPagamentoJaExecutado(){
		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		Pagamento pagamentoEditado = pagamentoCriado;
		pagamentoEditado.setContaDestino("oxe");
		pagamentoEditado.setValor(new BigDecimal(8888));


		givenAnExecutedEntity(pagamentoCriado);
		assertThrows(PagamentoNaoPodeSerExcluidoException.class,() -> pagamentoService.editarPagamento(pagamentoEditado)) ;
	}


	@Test
	void editarContaDeOrigemDoPagamento(){
		dadoUmPagamentoDTO();
		whenSavingTheEntity();
		var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);

		Pagamento pagamentoEditado = new Pagamento(pagamentoCriado.getId(),pagamentoCriado.getDataHora(),
				pagamentoCriado.getValor(), pagamentoCriado.getContaDestino(),
				pagamentoDTO.getContaOrigem(), pagamentoCriado.getExecutado());
		pagamentoEditado.setContaOrigem("oxe");
		pagamentoEditado.setValor(new BigDecimal(8888));


		whenGettingANonExecutedTheEntity(pagamentoCriado);
		assertThrows(EdicaoDeContaOrigemException.class,() -> pagamentoService.editarPagamento(pagamentoEditado)) ;
	}

	private void whenGettingANonExecutedTheEntity(Pagamento pagamentoCriado) {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(pagamentoCriado.getDataHora())
				.id(pagamentoCriado.getId())
				.valor(pagamentoCriado.getValor())
				.contaOrigem(pagamentoCriado.getContaOrigem())
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




	private void whenGettingANonExistentEntity() {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.empty());
	}

	private void whenDeletingTheEntity() {
		doNothing().when(pagamentoRepository).deleteById(Mockito.any());

	}


	private void whenEditingTheEntity(Pagamento pagamentoEditado) {
		when(pagamentoRepository.save(Mockito.any())).thenReturn(Pagamento.builder()
				.dataHora(pagamentoEditado.getDataHora())
				.id(pagamentoEditado.getId())
				.valor(pagamentoEditado.getValor())
				.contaOrigem(pagamentoDTO.getContaOrigem())
				.contaDestino(pagamentoEditado.getContaDestino())
				.executado(false)
				.build());

		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(pagamentoEditado.getDataHora())
				.id(pagamentoEditado.getId())
				.valor(pagamentoEditado.getValor())
				.contaOrigem(pagamentoEditado.getContaOrigem())
				.contaDestino(pagamentoDTO.getContaDestino())
				.executado(false)
				.build()));
	}



	private void givenAnExecutedEntity(Pagamento pagamentoCriado) {
		when(pagamentoRepository.findById(Mockito.any())).thenReturn(Optional.of(Pagamento.builder()
				.dataHora(pagamentoCriado.getDataHora())
				.id(pagamentoCriado.getId())
				.valor(pagamentoCriado.getValor())
				.contaOrigem(pagamentoCriado.getContaOrigem())
				.contaDestino(pagamentoCriado.getContaDestino())
				.executado(true)
				.build()));


	}

}