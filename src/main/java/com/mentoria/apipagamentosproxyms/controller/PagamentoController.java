package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.EdicaoDeContaOrigemException;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/pix/v1")
public class PagamentoController {
    @Autowired
    private final PagamentoService pagamentoService;

    @PostMapping("/pagamentos")
    public ResponseEntity<?> createPagamento(@Validated @RequestBody PagamentoDTO pagamentoDTO) {
        return new ResponseEntity<>(pagamentoService.criarPagamento(pagamentoDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/pagamentos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPagamento(@PathVariable UUID id) {
            pagamentoService.excluirPagamento(id);
    }

    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<?> editarPagamento(@PathVariable UUID id, @RequestBody PagamentoDTO pagamentoEditado) {
        return ResponseEntity.ok(pagamentoService.editarPagamento(id, pagamentoEditado));
    }

    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<?> obterPagamento(@PathVariable UUID id){
            return ResponseEntity.ok(pagamentoService.obterPagamento(id));
    }



}
