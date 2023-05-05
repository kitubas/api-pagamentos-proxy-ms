package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PagamentoControllerTest {


    @Mock
    PagamentoService pagamentoService;

    @InjectMocks
    PagamentoController pagamentoController;

    PagamentoDTO pagamentoDTO;
    void dadoUmPagamentoDTO(){
        pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setValor(new BigDecimal(99999));
        pagamentoDTO.setContaOrigem("Joao");
        pagamentoDTO.setContaDestino("Santana");
    }

    @Test
    void criarPagamentoComSucesso(){

        dadoUmPagamentoDTO();
        whenCreatingAPayment();

        ResponseEntity<Pagamento> pagamento = pagamentoController.createPagamento(pagamentoDTO);

        assertEquals(HttpStatus.CREATED,pagamento.getStatusCode());

    }

    private void whenCreatingAPayment() {

        when(pagamentoService.criarPagamento(pagamentoDTO)).thenReturn(Pagamento.builder()
                .dataHora(LocalDateTime.now().toString())
                .id(UUID.randomUUID())
                .valor(new BigDecimal(99999))
                .contaOrigem(pagamentoDTO.getContaOrigem())
                .contaDestino(pagamentoDTO.getContaDestino())
                .executado(false)
                .build());

    }
}
