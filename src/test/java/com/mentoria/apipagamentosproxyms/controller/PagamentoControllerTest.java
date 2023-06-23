package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.EdicaoDeContaOrigemException;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles(value = "test")
public class PagamentoControllerTest {


    @Autowired
    MockMvc mockMvc;
    @MockBean
    PagamentoService pagamentoService;

    PagamentoDTO pagamentoDTO;
    @BeforeEach
    void dadoUmPagamentoDTO(){
        pagamentoDTO = new PagamentoDTO(new BigDecimal(99999),"Joao","Santana");
    }


    String pagamentoDTOJson(){
        return "{\n" +
                "  \"valor\": \"" + pagamentoDTO.valor() + "\",\n" +
                "  \"contaOrigem\": \"" + pagamentoDTO.contaOrigem() + "\",\n" +
                "  \"contaDestino\": \"" + pagamentoDTO.contaDestino() + "\"\n" +
                "}";
    }
    @Test
    void criarPagamentoComSucesso() throws Exception {
        whenCreatingAPayment();

        mockMvc.perform(post("/pix/v1/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoDTOJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.valor", equalTo(pagamentoDTO.valor().intValue())))
                .andExpect(jsonPath("$.contaOrigem", equalTo(pagamentoDTO.contaOrigem())))
                .andExpect(jsonPath("$.contaDestino", is(pagamentoDTO.contaDestino())));
    }

    @Test
    void excluirPagamentoComSucesso() throws Exception {
        whenDeletingAPayment();
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/pix/v1/pagamentos/" + id.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void editarPagamentoComSucesso() throws Exception {
        Pagamento pagamento = givenAPayment();
        whenEditingAPayment(pagamento);


        mockMvc.perform(put("/pix/v1/pagamentos/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoDTOJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pagamento.getId().toString())))
                .andExpect(jsonPath("$.valor", equalTo(pagamento.getValor().intValue())))
                .andExpect(jsonPath("$.contaDestino", is(pagamento.getContaDestino())));
    }

    @Test
    void editarPagamentoJaExecutado() throws Exception {
        givenANAlreadyExecutedPayment();

        mockMvc.perform(put("/pix/v1/pagamentos/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoDTOJson()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void editarContaDeOrigemDoPagamento() throws Exception {
        whenEditingOriginOfPayment();

        mockMvc.perform(put("/pix/v1/pagamentos/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoDTOJson()))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void exluirPagamentoJaExecutado() throws Exception {
        givenANAlreadyExecutedPayment();

        mockMvc.perform(delete("/pix/v1/pagamentos/" + UUID.randomUUID().toString()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void criarPagamentoFaltandoCampos() throws Exception {
        String requestBody = "{\n" +
                "  \"contaOrigem\": \"" + pagamentoDTO.contaOrigem() + "\",\n" +
                "  \"contaDestino\": \"" + pagamentoDTO.contaDestino() + "\"\n" +
                "}";

        mockMvc.perform(post("/pix/v1/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void excluirPagamentoNaoEncontrado() throws Exception {
        givenANotFoundPayment();

        mockMvc.perform(delete("/pix/v1/pagamentos/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void editarPagamentoNaoEncontrado() throws Exception {
        givenANotFoundPayment();

        mockMvc.perform(put("/pix/v1/pagamentos/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoDTOJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    void obterPagamento() throws Exception {
        Pagamento pagamento = givenAPayment();
        whenGettingAPayment(pagamento);

        mockMvc.perform(get("/pix/v1/pagamentos/" + UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pagamento.getId().toString())))
                .andExpect(jsonPath("$.valor", equalTo(pagamento.getValor().intValue())))
                .andExpect(jsonPath("$.contaDestino", is(pagamento.getContaDestino())));
    }

    @Test
    void obterPagamentoNaoEncontrado() throws Exception {
        whenGettingANotFoundPayment();

        mockMvc.perform(get("/pix/v1/pagamentos/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    private void whenGettingANotFoundPayment() {
        when(pagamentoService.obterPagamento(Mockito.any())).thenThrow(NoSuchElementException.class);
    }

    private void whenGettingAPayment(Pagamento pagamento) {
        when(pagamentoService.obterPagamento(Mockito.any())).thenReturn(pagamento);
    }

    private void givenANotFoundPayment() {
        when(pagamentoService.editarPagamento(Mockito.any(), Mockito.any())).thenThrow(NoSuchElementException.class);
        doThrow(NoSuchElementException.class).when(pagamentoService).excluirPagamento(Mockito.any());
    }

    private void whenEditingOriginOfPayment() {
        when(pagamentoService.editarPagamento(Mockito.any(), Mockito.any())).thenThrow(EdicaoDeContaOrigemException.class);
    }

    private void givenANAlreadyExecutedPayment() {
        when(pagamentoService.editarPagamento(Mockito.any(), Mockito.any())).thenThrow(PagamentoNaoPodeSerExcluidoException.class);
        doThrow(PagamentoNaoPodeSerExcluidoException.class).when(pagamentoService).excluirPagamento(Mockito.any());
    }

    private void whenEditingAPayment(Pagamento pagamento) {
        when(pagamentoService.editarPagamento(Mockito.any(), Mockito.any())).thenReturn( pagamento);
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
