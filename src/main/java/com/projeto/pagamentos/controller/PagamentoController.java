package com.projeto.pagamentos.controller;

import com.projeto.pagamentos.dto.AtualizarStatusDTO;
import com.projeto.pagamentos.dto.PagamentoRequestDTO;
import com.projeto.pagamentos.dto.PagamentoResponseDTO;
import com.projeto.pagamentos.model.StatusPagamento;
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

    @PutMapping("/status")
    public void atualizarStatus(@RequestBody AtualizarStatusDTO dto) {
        service.atualizarStatus(dto);
    }

    @DeleteMapping("/{id}")
    public void excluirPagamento(@PathVariable Long id) {
        service.excluirPagamento(id);
    }


    @GetMapping
    public List<PagamentoResponseDTO> listarTodos(
            @RequestParam(required = false) Integer codigoDebito,
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) StatusPagamento status) {
        return service.listarTodos(codigoDebito, cpfCnpj, status);

}
}

