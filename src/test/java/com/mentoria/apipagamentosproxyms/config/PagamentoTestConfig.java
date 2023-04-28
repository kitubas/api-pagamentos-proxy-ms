package com.mentoria.apipagamentosproxyms.config;

import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import com.mentoria.apipagamentosproxyms.service.PagamentoServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class PagamentoTestConfig {

    @Bean
    public PagamentoService pagamentoService(){

        return new PagamentoServiceImpl();
    }
}
