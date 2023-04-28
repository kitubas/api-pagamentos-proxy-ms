package com.mentoria.apipagamentosproxyms.respository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mentoria.apipagamentosproxyms.model.Pagamento;

@Repository
public interface PagamentoRepository extends CrudRepository<Pagamento, UUID>  {

}
