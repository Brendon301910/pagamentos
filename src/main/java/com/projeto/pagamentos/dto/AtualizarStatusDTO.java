package com.projeto.pagamentos.dto;
import com.projeto.pagamentos.model.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusDTO {
    private Long id;
    private StatusPagamento status;
}
