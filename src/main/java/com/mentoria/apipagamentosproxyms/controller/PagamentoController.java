package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@RestController
@RequestMapping("/pix/v1")
public class PagamentoController {
    @Autowired
    private final PagamentoService pagamentoService;
    @Autowired
    private final PagamentoMapper pagamentoMapper;

    @PostMapping("/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@Validated @RequestBody PagamentoDTO pagamentoDTO) {

        return new ResponseEntity<Pagamento>(pagamentoService.criarPagamento(pagamentoDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/pagamentos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPagamento(@PathVariable UUID id) {
        pagamentoService.excluirPagamento(id);
    }

    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<Pagamento> editarPagamento(@PathVariable UUID id, @RequestBody PagamentoDTO pagamentoEditado) {
        return new ResponseEntity<>(pagamentoService.editarPagamento(id, pagamentoEditado), HttpStatus.OK);
    }


}
