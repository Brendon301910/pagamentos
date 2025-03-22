package com.projeto.pagamentos.controller;

import com.projeto.pagamentos.dto.AtualizarStatusDTO;
import com.projeto.pagamentos.dto.PagamentoRequestDTO;
import com.projeto.pagamentos.dto.PagamentoResponseDTO;
import com.projeto.pagamentos.service.PagamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    public PagamentoResponseDTO criarPagamento(@RequestBody PagamentoRequestDTO dto) {
        return service.criarPagamento(dto);
    }

    // Corrigido o PUT com a barra antes de 'status'
    @PutMapping("/status")
    public void atualizarStatus(@RequestBody AtualizarStatusDTO dto) {
        service.atualizarStatus(dto);
    }

    // Corrigido o GET para usar a rota correta '/pagamentos'
    @GetMapping
    public List<PagamentoResponseDTO> listarTodos() {
        return service.listarTodos();
    }
}

