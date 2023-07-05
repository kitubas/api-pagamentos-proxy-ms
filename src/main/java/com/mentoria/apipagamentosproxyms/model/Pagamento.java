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
@Data
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
    @NonNull
    private String tipoPagamento;
    private String dataHoraExecucao;

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
