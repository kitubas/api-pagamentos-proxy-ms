package com.mentoria.apipagamentosproxyms.mapper;

import org.springframework.stereotype.Component;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.model.Pagamento;

@Component
public class PagamentoMapper {

    public Pagamento pagamentoDtoToModel(PagamentoDTO pagamentoDTO) {
        return Pagamento.builder()
                .valor(
                        pagamentoDTO.valor())
                .contaDestino(
                        pagamentoDTO.contaDestino())
                .contaOrigem(
                        pagamentoDTO.contaOrigem())
                .tipoPagamento(
                        pagamentoDTO.tipoPagamento())
                .dataHoraExecucao(
                        pagamentoDTO.dataHoraExecucao())
                .build();
    }

}
