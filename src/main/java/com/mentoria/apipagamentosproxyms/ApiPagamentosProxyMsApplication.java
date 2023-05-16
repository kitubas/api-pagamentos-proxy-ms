package com.mentoria.apipagamentosproxyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.mentoria.apipagamentosproxyms.respository")
public class ApiPagamentosProxyMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPagamentosProxyMsApplication.class, args);
	}

}
