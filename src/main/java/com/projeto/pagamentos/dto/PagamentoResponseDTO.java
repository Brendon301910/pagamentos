package com.projeto.pagamentos.dto;
import com.projeto.pagamentos.model.MetodoPagamento;
import com.projeto.pagamentos.model.StatusPagamento;
import java.math.BigDecimal;

public record PagamentoResponseDTO(
        Long id,
        Integer codigoDebito,
        String cpfCnpj,
        MetodoPagamento metodoPagamento,
        BigDecimal valor,
        StatusPagamento status
) {}
