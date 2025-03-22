package com.projeto.pagamentos.dto;
import com.projeto.pagamentos.model.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
        @NotNull Long idPagamento,
        @NotNull StatusPagamento novoStatus
) {}
