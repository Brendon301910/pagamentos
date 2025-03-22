package com.projeto.pagamentos.dto;
import com.projeto.pagamentos.model.MetodoPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
        @NotNull Integer codigoDebito,
        @NotBlank String cpfCnpj,
        @NotNull MetodoPagamento metodoPagamento,
        String numeroCartao,
        @NotNull @DecimalMin("0.01") BigDecimal valor
) {}
