package com.mentoria.apipagamentosproxyms.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String dataHora;
    @NonNull
    private BigDecimal valor;
    @NonNull
    private String contaDestino;

    @NonNull
    private String contaOrigem;

    private Boolean executado;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Boolean getExecutado() {
        return executado;
    }

    public void setExecutado(Boolean executado) {
        this.executado = executado;
    }

    @Override
    public boolean equals(Object obj) {

    	if (obj instanceof Pagamento pagamento) {

            return pagamento.getId().equals(this.id)
                    && pagamento.getContaOrigem().equals(this.contaOrigem)
                    && pagamento.getDataHora().equals(this.dataHora)
                    && pagamento.getContaDestino().equals(this.contaDestino)
                    && pagamento.getValor().compareTo(this.valor)==0
                    && pagamento.getExecutado().equals(this.executado);
		}
    	return false;
    }


//    return switch (obj){
//        case Pagamento pagamento when pagamento.getId().equals(this.id)
//                && pagamento.getContaOrigem().equals(this.contaOrigem)
//                && pagamento.getDataHora().equals(this.dataHora)
//                && pagamento.getContaDestino().equals(this.contaDestino)
//                && pagamento.getValor().compareTo(this.valor)==0
//                && pagamento.getExecutado().equals(this.executado) -> true;
//        default -> false;
//    };
}
