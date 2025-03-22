package com.projeto.pagamentos.service;
import com.projeto.pagamentos.dto.AtualizarStatusDTO;
import com.projeto.pagamentos.dto.PagamentoRequestDTO;
import com.projeto.pagamentos.dto.PagamentoResponseDTO;
import com.projeto.pagamentos.model.Pagamento;
import com.projeto.pagamentos.model.StatusPagamento;
import com.projeto.pagamentos.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoDebito(dto.codigoDebito());
        pagamento.setCpfCnpj(dto.cpfCnpj());
        pagamento.setMetodoPagamento(dto.metodoPagamento());
        pagamento.setNumeroCartao(dto.numeroCartao());
        pagamento.setValor(dto.valor());
        pagamento.setStatus(StatusPagamento.PENDENTE);

        Pagamento salvo = repository.save(pagamento);
        return new PagamentoResponseDTO(
                salvo.getId(), salvo.getCodigoDebito(), salvo.getCpfCnpj(), salvo.getMetodoPagamento(),
                salvo.getValor(), salvo.getStatus()
        );
    }

    @Transactional
    public PagamentoResponseDTO atualizarStatus(AtualizarStatusDTO dto) {
        System.out.println("Atualizando status para pagamento ID: " + dto.idPagamento());  // Adicione um log
        Pagamento pagamento = repository.findById(dto.idPagamento())
                .orElseThrow(() -> new RuntimeException("Pagamento com ID " + dto.idPagamento() + " não encontrado."));

        if (pagamento.getStatus() == StatusPagamento.PROCESSADO_SUCESSO) {
            throw new IllegalStateException("Pagamentos processados com sucesso não podem ser alterados.");
        }

        if (pagamento.getStatus() == StatusPagamento.PROCESSADO_FALHA && dto.novoStatus() != StatusPagamento.PENDENTE) {
            throw new IllegalStateException("Pagamentos com falha só podem voltar para PENDENTE.");
        }

        pagamento.setStatus(dto.novoStatus());
        Pagamento salvo = repository.save(pagamento);

        return new PagamentoResponseDTO(
                salvo.getId(), salvo.getCodigoDebito(), salvo.getCpfCnpj(), salvo.getMetodoPagamento(),
                salvo.getValor(), salvo.getStatus()
        );
    }



    public List<PagamentoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(p -> new PagamentoResponseDTO(p.getId(), p.getCodigoDebito(), p.getCpfCnpj(), p.getMetodoPagamento(), p.getValor(), p.getStatus()))
                .collect(Collectors.toList());
    }
}

