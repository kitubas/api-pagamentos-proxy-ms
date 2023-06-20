package com.mentoria.apipagamentosproxyms.config;

import com.mentoria.apipagamentosproxyms.annotations.SendSNSMessages;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;
import com.mentoria.apipagamentosproxyms.service.PagamentoService;
import com.mentoria.apipagamentosproxyms.service.PagamentoServiceImpl;
import com.mentoria.apipagamentosproxyms.service.SNSEventPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
@Configuration
public class PagamentoServiceConfig {

    @Autowired
    PagamentoRepository repository;

    @Autowired
    PagamentoMapper pagamentoMapper;
    @Autowired
    SNSEventPublisherService snsEventPublisherService;

    @Bean
    public PagamentoService pagamentoService(){
        PagamentoServiceImpl pagamentoService = new PagamentoServiceImpl(repository,pagamentoMapper,snsEventPublisherService);
        for (Field field: pagamentoService.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SendSNSMessages.class)){
                System.out.println("manda msgs sns");
            }
        }
        return pagamentoService;
    }

}
