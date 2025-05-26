package br.com.l2code.servico_de_empacotamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.l2code.servico_de_empacotamento.dao.PedidoRepository;
import br.com.l2code.servico_de_empacotamento.models.Pedido;
import br.com.l2code.servico_de_empacotamento.models.Produto;
import br.com.l2code.servico_de_empacotamento.models.dtos.CaixaResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.DimensaoDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.PedidoDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.PedidoResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ProdutoDTO;
import br.com.l2code.servico_de_empacotamento.services.EmpacotamentoServiceImpl;

/**
 * Testes unitários para a classe EmpacotamentoServiceImpl.
 */
// A classe EmpacotamentoServiceImpl é responsável por processar pedidos e
// empacotar produtos
// em caixas, utilizando o repositório PedidoRepository para persistir os dados.
@ExtendWith(MockitoExtension.class)
class EmpacotamentoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private EmpacotamentoServiceImpl empacotamentoService;

    private ListaPedidosDTO ListaPedidosDTO;
    private Pedido pedidoSalvo;

    @BeforeEach
    void setUp() {
        // Configurar dados de teste
        DimensaoDTO dimensoes1 = new DimensaoDTO(10, 10, 10);
        DimensaoDTO dimensoes2 = new DimensaoDTO(20, 20, 20);

        ProdutoDTO produto1 = new ProdutoDTO("PS5", dimensoes1);
        ProdutoDTO produto2 = new ProdutoDTO("XBOX", dimensoes2);

        PedidoDTO pedido = new PedidoDTO(123, Arrays.asList(produto1, produto2));
        ListaPedidosDTO = new ListaPedidosDTO(Arrays.asList(pedido));

        // Configurar pedido salvo
        pedidoSalvo = new Pedido(123);
        pedidoSalvo.setId(1L);

        Produto produtoEntity1 = new Produto("PS5", 10, 10, 10);
        Produto produtoEntity2 = new Produto("XBOX", 20, 20, 20);

        pedidoSalvo.adicionarProduto(produtoEntity1);
        pedidoSalvo.adicionarProduto(produtoEntity2);
    }

    @Test
    void deveProcessarPedidosComSucesso() {

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);

        ListaPedidosResponseDTO response = empacotamentoService.processarPedidos(ListaPedidosDTO);

        assertNotNull(response);
        assertEquals(1, response.getPedidos().size());

        PedidoResponseDTO pedidoResponse = response.getPedidos().get(0);
        assertEquals(123, pedidoResponse.getPedidoId());
        assertFalse(pedidoResponse.getCaixas().isEmpty());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void deveEmpacotarProdutosPequenos() {
        // Given - produtos pequenos que cabem na Caixa 1
        DimensaoDTO dimensoesPequenas = new DimensaoDTO(5, 5, 5);
        ProdutoDTO produtoPequeno1 = new ProdutoDTO("ITEM1", dimensoesPequenas);
        ProdutoDTO produtoPequeno2 = new ProdutoDTO("ITEM2", dimensoesPequenas);

        PedidoDTO pedido = new PedidoDTO(456, Arrays.asList(produtoPequeno1, produtoPequeno2));
        ListaPedidosDTO lista = new ListaPedidosDTO(Arrays.asList(pedido));

        Pedido pedidoComProdutosPequenos = new Pedido(456);
        pedidoComProdutosPequenos.adicionarProduto(new Produto("ITEM1", 5, 5, 5));
        pedidoComProdutosPequenos.adicionarProduto(new Produto("ITEM2", 5, 5, 5));

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoComProdutosPequenos);

        ListaPedidosResponseDTO response = empacotamentoService.processarPedidos(lista);

        PedidoResponseDTO pedidoResponse = response.getPedidos().get(0);
        assertEquals(2, pedidoResponse.getCaixas().size(), "Deve haver duas caixas");

        // Validar que ambas as caixas contém um produto cada
        List<CaixaResponseDTO> caixas = pedidoResponse.getCaixas();
        boolean caixa1ContemItem1 = caixas.stream().anyMatch(c -> c.getProdutos().contains("ITEM1"));
        boolean caixa2ContemItem2 = caixas.stream().anyMatch(c -> c.getProdutos().contains("ITEM2"));

        assertTrue(caixa1ContemItem1, "Deve ter caixa com ITEM1");
        assertTrue(caixa2ContemItem2, "Deve ter caixa com ITEM2");
    }

    @Test
    void deveGerarObservacaoParaProdutoQueNaoCabe() {
        // Given - produto muito grande
        DimensaoDTO dimensoesGrandes = new DimensaoDTO(100, 100, 100);
        ProdutoDTO produtoGrande = new ProdutoDTO("GELADEIRA", dimensoesGrandes);

        PedidoDTO pedido = new PedidoDTO(789, Arrays.asList(produtoGrande));
        ListaPedidosDTO lista = new ListaPedidosDTO(Arrays.asList(pedido));

        Pedido pedidoComProdutoGrande = new Pedido(789);
        pedidoComProdutoGrande.adicionarProduto(new Produto("GELADEIRA", 100, 100, 100));

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoComProdutoGrande);

        // When
        ListaPedidosResponseDTO response = empacotamentoService.processarPedidos(lista);

        // Then
        PedidoResponseDTO pedidoResponse = response.getPedidos().get(0);
        assertEquals(1, pedidoResponse.getCaixas().size());

        CaixaResponseDTO caixa = pedidoResponse.getCaixas().get(0);
        assertNull(caixa.getCaixaId());
        assertEquals("Produto não cabe em nenhuma caixa disponível.", caixa.getObservacao());
        assertTrue(caixa.getProdutos().contains("GELADEIRA"));
    }

    @Test
    void deveProcessarMultiplosPedidos() {

        PedidoDTO pedido1 = new PedidoDTO(111, Arrays.asList(
                new ProdutoDTO("ITEM1", new DimensaoDTO(5, 5, 5))));
        PedidoDTO pedido2 = new PedidoDTO(222, Arrays.asList(
                new ProdutoDTO("ITEM2", new DimensaoDTO(10, 10, 10))));

        ListaPedidosDTO lista = new ListaPedidosDTO(Arrays.asList(pedido1, pedido2));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenReturn(criarPedidoMock(111, "ITEM1"))
                .thenReturn(criarPedidoMock(222, "ITEM2"));

        ListaPedidosResponseDTO response = empacotamentoService.processarPedidos(lista);

        assertEquals(2, response.getPedidos().size());
        verify(pedidoRepository, times(2)).save(any(Pedido.class));
    }

    private Pedido criarPedidoMock(Integer pedidoId, String produtoId) {
        Pedido pedido = new Pedido(pedidoId);
        pedido.adicionarProduto(new Produto(produtoId, 5, 5, 5));
        return pedido;
    }
}