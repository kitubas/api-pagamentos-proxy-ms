package com.mentoria.apipagamentosproxyms.config;

import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import com.mentoria.apipagamentosproxyms.service.PagamentoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoConfig {

    @Bean
    public PagamentoService pagamentoService(){

        return new PagamentoServiceImpl();
    }
}
