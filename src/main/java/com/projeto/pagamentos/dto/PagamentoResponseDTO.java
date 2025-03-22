package com.projeto.pagamentos.dto;

import com.projeto.pagamentos.model.MetodoPagamento;
import com.projeto.pagamentos.model.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PagamentoResponseDTO {
    private Long id;
    private Integer codigoDebito;
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;
    private BigDecimal valor;
    private StatusPagamento status;
}
