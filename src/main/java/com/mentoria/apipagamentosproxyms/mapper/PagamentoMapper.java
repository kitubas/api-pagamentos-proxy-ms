package com.mentoria.apipagamentosproxyms.mapper;

import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.model.Pagamento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagamentoMapper {

    public Pagamento pagamentoDtoToModel(PagamentoDTO pagamentoDTO){
        return new Pagamento(pagamentoDTO.valor(),
                pagamentoDTO.contaDestino(),
                pagamentoDTO.contaOrigem());
    }

}
