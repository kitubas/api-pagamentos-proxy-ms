package com.mentoria.apipagamentosproxyms.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoDTO {

    private BigDecimal valor;
    private String contaDestino;

    private String contaOrigem;

}
