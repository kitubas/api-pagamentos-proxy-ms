package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {


    @Mock
    PagamentoService pagamentoService;

    @InjectMocks
    PagamentoController pagamentoController;

    @Autowired
    MockMvc mockMvc;

    PagamentoDTO pagamentoDTO;
    void dadoUmPagamentoDTO(){
        pagamentoDTO = new PagamentoDTO(new BigDecimal(99999),"Joao","Santana");
    }

    @Test
    void criarPagamentoComSucesso(){

        dadoUmPagamentoDTO();
        whenCreatingAPayment();

        ResponseEntity<Pagamento> pagamento = pagamentoController.createPagamento(pagamentoDTO);

        assertEquals(HttpStatus.CREATED,pagamento.getStatusCode());

    }

    @Test
    void excluirPagamentoComSucesso() throws Exception {
        whenDeletingAPayment();
        UUID id = UUID.randomUUID();
        pagamentoController.excluirPagamento(id);
        verify(pagamentoService, times(1)).excluirPagamento(id);

        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
        mockMvc.perform(delete("/pix/v1/delete/" + id.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void editarPagamentoComSucesso(){
        Pagamento pagamento = givenAPayment();
        whenEditingAPayment(pagamento);
//        ResponseEntity<Pagamento> pagamentoRetornado = pagamentoController.editarPagamento(Mockito.any());
//
//        verify(pagamentoService,times(1)).editarPagamento(Mockito.any());
//
//        mockMvc.perform(patch("/pix/v1/edit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nome\":\"" + suaEntidadeAtualizada.getNome() + "\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(suaEntidadeAtualizada.getId().intValue())))
//                .andExpect(jsonPath("$.nome", is(suaEntidadeAtualizada.getNome())));


    }

    @Test
    void editarPagamentoJaExecutado(){
        givenANAlreadyExecutedPayment();

//        assertThrows(PagamentoNaoPodeSerExcluidoException.class,() -> pagamentoController.editarPagamento(Mockito.any()));

    }

    private void givenANAlreadyExecutedPayment() {
//        when(pagamentoService.editarPagamento(Mockito.any())).thenThrow(PagamentoNaoPodeSerExcluidoException.class);
    }

    private void whenEditingAPayment(Pagamento pagamento) {
//        when(pagamentoService.editarPagamento(Mockito.any())).thenReturn( pagamento);
    }

    private Pagamento givenAPayment() {

        return Pagamento.builder()
                .dataHora(LocalDateTime.now().toString())
                .id(UUID.randomUUID())
                .valor(new BigDecimal(99999))
                .contaOrigem("Joao")
                .contaDestino("Santana")
                .executado(false)
                .build();
    }

    private void whenDeletingAPayment() {
        doNothing().when(pagamentoService).excluirPagamento(Mockito.any());
    }


    private void whenCreatingAPayment() {

        when(pagamentoService.criarPagamento(pagamentoDTO)).thenReturn(Pagamento.builder()
                .dataHora(LocalDateTime.now().toString())
                .id(UUID.randomUUID())
                .valor(new BigDecimal(99999))
                .contaOrigem(pagamentoDTO.contaOrigem())
                .contaDestino(pagamentoDTO.contaDestino())
                .executado(false)
                .build());

    }
}
