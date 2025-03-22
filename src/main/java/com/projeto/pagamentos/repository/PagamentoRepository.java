package com.projeto.pagamentos.repository;
import com.projeto.pagamentos.model.Pagamento;
import com.projeto.pagamentos.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByCodigoDebito(Integer codigoDebito);
    List<Pagamento> findByCpfCnpj(String cpfCnpj);
    List<Pagamento> findByStatus(StatusPagamento status);
}

