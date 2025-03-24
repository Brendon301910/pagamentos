package com.projeto.pagamentos;

import com.projeto.pagamentos.dto.AtualizarStatusDTO;
import com.projeto.pagamentos.dto.PagamentoRequestDTO;
import com.projeto.pagamentos.dto.PagamentoResponseDTO;
import com.projeto.pagamentos.model.MetodoPagamento;
import com.projeto.pagamentos.model.Pagamento;
import com.projeto.pagamentos.model.StatusPagamento;
import com.projeto.pagamentos.repository.PagamentoRepository;
import com.projeto.pagamentos.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

	@Mock
	private PagamentoRepository repository;

	@InjectMocks
	private PagamentoService service;

	private Pagamento pagamento;
	private Pagamento pagamento2;

	@BeforeEach
	void setUp() {
		pagamento = new Pagamento();
		pagamento.setId(1L);
		pagamento.setCodigoDebito(123456);
		pagamento.setCpfCnpj("12345678900");
		pagamento.setMetodoPagamento(MetodoPagamento.valueOf("CARTAO_CREDITO"));
		pagamento.setNumeroCartao("4111111111111111");
		pagamento.setValor(BigDecimal.valueOf(100));
		pagamento.setStatus(StatusPagamento.PENDENTE);

		pagamento2 = new Pagamento();
		pagamento2.setId(2L);
		pagamento2.setCodigoDebito(654321);
		pagamento2.setCpfCnpj("09876543211");
		pagamento.setMetodoPagamento(MetodoPagamento.valueOf("BOLETO"));
		pagamento2.setValor(new BigDecimal("200.0"));
		pagamento2.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
	}

	@Test
	void deveCriarPagamentoComSucesso() {
		PagamentoRequestDTO requestDTO = new PagamentoRequestDTO(
				123456, "12345678900", MetodoPagamento.CARTAO_CREDITO, "4111111111111111", BigDecimal.valueOf(100.0)
		);

		when(repository.save(any(Pagamento.class))).thenReturn(pagamento);

		PagamentoResponseDTO response = service.criarPagamento(requestDTO);

		assertNotNull(response);
		assertEquals(123456, response.getCodigoDebito());
		assertEquals(StatusPagamento.PENDENTE, response.getStatus());

		verify(repository, times(1)).save(any(Pagamento.class));
	}

	@Test
	void deveAtualizarStatusPagamentoComSucesso() {
		AtualizarStatusDTO dto = new AtualizarStatusDTO();
		dto.setId(1L);
		dto.setStatus(StatusPagamento.PROCESSADO_SUCESSO);

		when(repository.findById(1L)).thenReturn(Optional.of(pagamento));
		when(repository.save(any(Pagamento.class))).thenReturn(pagamento);

		PagamentoResponseDTO response = service.atualizarStatus(dto);

		assertNotNull(response);
		assertEquals(StatusPagamento.PROCESSADO_SUCESSO, response.getStatus());

		verify(repository, times(1)).findById(1L);
		verify(repository, times(1)).save(any(Pagamento.class));
	}

	@Test
	void deveLancarExcecaoSePagamentoNaoEncontradoAoAtualizarStatus() {
		AtualizarStatusDTO dto = new AtualizarStatusDTO();
		dto.setId(999L);
		dto.setStatus(StatusPagamento.PROCESSADO_SUCESSO);

		when(repository.findById(999L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> service.atualizarStatus(dto));

		assertEquals("Pagamento com ID 999 não encontrado.", exception.getMessage());

		verify(repository, times(1)).findById(999L);
		verify(repository, never()).save(any(Pagamento.class));
	}

	@Test
	void deveLancarExcecaoSeTentarAtualizarPagamentoJaSucesso() {
		AtualizarStatusDTO dto = new AtualizarStatusDTO();
		dto.setId(1L);
		dto.setStatus(StatusPagamento.PROCESSADO_FALHA);

		pagamento.setStatus(StatusPagamento.PROCESSADO_SUCESSO);

		when(repository.findById(1L)).thenReturn(Optional.of(pagamento));

		Exception exception = assertThrows(IllegalStateException.class, () -> service.atualizarStatus(dto));

		assertEquals("Pagamentos processados com sucesso não podem ser alterados.", exception.getMessage());

		verify(repository, times(1)).findById(1L);
		verify(repository, never()).save(any(Pagamento.class));
	}
	@Test
	void deveListarTodosOsPagamentos() {
		when(repository.findAll()).thenReturn(Arrays.asList(pagamento, pagamento2));

		List<PagamentoResponseDTO> result = service.listarTodos(null, null, null);

		assertEquals(2, result.size());
		verify(repository, times(1)).findAll();
	}

	@Test
	void deveListarPagamentosPorCodigoDebito() {
		when(repository.findByCodigoDebito(123456)).thenReturn(List.of(pagamento));

		List<PagamentoResponseDTO> result = service.listarTodos(123456, null, null);

		assertEquals(1, result.size());
		assertEquals(123456, result.get(0).getCodigoDebito());
		verify(repository, times(1)).findByCodigoDebito(123456);
	}

	@Test
	void deveListarPagamentosPorCpfCnpj() {
		when(repository.findByCpfCnpj("12345678900")).thenReturn(List.of(pagamento));

		List<PagamentoResponseDTO> result = service.listarTodos(null, "12345678900", null);

		assertEquals(1, result.size());
		assertEquals("12345678900", result.get(0).getCpfCnpj());
		verify(repository, times(1)).findByCpfCnpj("12345678900");
	}

	@Test
	void deveListarPagamentosPorStatus() {
		when(repository.findByStatus(StatusPagamento.PENDENTE)).thenReturn(List.of(pagamento));

		List<PagamentoResponseDTO> result = service.listarTodos(null, null, StatusPagamento.PENDENTE);

		assertEquals(1, result.size());
		assertEquals(StatusPagamento.PENDENTE, result.get(0).getStatus());
		verify(repository, times(1)).findByStatus(StatusPagamento.PENDENTE);
	}

	@Test
	void deveExcluirPagamentoLogicamente() {
		when(repository.findById(1L)).thenReturn(Optional.of(pagamento));

		service.excluirPagamento(1L);

		assertEquals(StatusPagamento.INATIVO, pagamento.getStatus());
		verify(repository, times(1)).save(pagamento);
	}

	@Test
	void naoDeveExcluirPagamentoJaInativo() {
		pagamento.setStatus(StatusPagamento.INATIVO);
		when(repository.findById(1L)).thenReturn(Optional.of(pagamento));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.excluirPagamento(1L));

		assertEquals("O pagamento já foi excluído.", exception.getMessage());
		verify(repository, never()).save(pagamento);
	}
}

