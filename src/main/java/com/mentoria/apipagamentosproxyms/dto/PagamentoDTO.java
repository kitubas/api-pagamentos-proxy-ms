package com.mentoria.apipagamentosproxyms.dto;


import lombok.NonNull;

import java.math.BigDecimal;

public record PagamentoDTO(@NonNull BigDecimal valor, @NonNull String contaOrigem, @NonNull String contaDestino) {}