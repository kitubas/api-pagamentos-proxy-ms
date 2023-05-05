package com.mentoria.apipagamentosproxyms.controller;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/pix/v1")
public class PagamentoController {


    private final PagamentoService pagamentoService;

    public ResponseEntity<Pagamento> createPagamento(@RequestBody PagamentoDTO pagamentoDTO){

        return new ResponseEntity<Pagamento>(pagamentoService.criarPagamento(pagamentoDTO), HttpStatus.CREATED);


    }


}
