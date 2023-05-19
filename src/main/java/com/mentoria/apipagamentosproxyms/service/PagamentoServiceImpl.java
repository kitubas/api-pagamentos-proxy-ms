package com.mentoria.apipagamentosproxyms.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mentoria.apipagamentosproxyms.annotations.SendSNSMessages;
import com.mentoria.apipagamentosproxyms.dto.PagamentoDTO;
import com.mentoria.apipagamentosproxyms.exceptions.EdicaoDeContaOrigemException;
import com.mentoria.apipagamentosproxyms.exceptions.PagamentoNaoPodeSerExcluidoException;
import com.mentoria.apipagamentosproxyms.mapper.PagamentoMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentoria.apipagamentosproxyms.model.Pagamento;
import com.mentoria.apipagamentosproxyms.respository.PagamentoRepository;

@AllArgsConstructor
@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    PagamentoRepository repository;

    @Autowired
    PagamentoMapper pagamentoMapper;

//    @Autowired
//    SQSEventPublisherService sqsEventPublisherService;


    @Autowired
    SNSEventPublisherService snsEventPublisherService;

    @Override
    public Pagamento criarPagamento(PagamentoDTO pagamentoDTO) {

        Pagamento pagamento = pagamentoMapper.pagamentoDtoToModel(pagamentoDTO);
        pagamento.setDataHora(LocalDateTime.now().toString());
        pagamento.setExecutado(false);
        Pagamento savedPagamento = repository.save(pagamento);
//        sqsEventPublisherService.enviarMensagemStandardQueue(savedPagamento);
        snsEventPublisherService.enviarMensagemSns(savedPagamento);
        return savedPagamento;

    }

    @Override
    public Pagamento obterPagamento(UUID uuid) {
        return repository.findById(uuid).orElseThrow();
    }

    @Override
    public void excluirPagamento(UUID id) {
        Pagamento pagamento = obterPagamento(id);
        if (pagamento.getExecutado()) {
            throw new PagamentoNaoPodeSerExcluidoException();
        }
        repository.deleteById(id);

    }

    @Override
    public Pagamento editarPagamento(UUID id, PagamentoDTO pagamentoEditado) {
        Pagamento pagamento = obterPagamento(id);
        if (pagamento.getExecutado()) {
            throw new PagamentoNaoPodeSerExcluidoException();
        }
        if (!pagamentoEditado.contaOrigem().equals(pagamento.getContaOrigem())) {
            throw new EdicaoDeContaOrigemException();
        }
        pagamento.setValor(pagamentoEditado.valor());
        pagamento.setContaDestino(pagamentoEditado.contaDestino());
        return repository.save(pagamento);


    }
}
