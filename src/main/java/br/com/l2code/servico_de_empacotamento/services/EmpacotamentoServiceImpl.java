package br.com.l2code.servico_de_empacotamento.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.l2code.servico_de_empacotamento.dao.PedidoRepository;
import br.com.l2code.servico_de_empacotamento.enumareted.TipoCaixa;
import br.com.l2code.servico_de_empacotamento.models.Pedido;
import br.com.l2code.servico_de_empacotamento.models.Produto;
import br.com.l2code.servico_de_empacotamento.models.dtos.CaixaResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.DimensaoDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.PedidoDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.PedidoResponseDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ProdutoDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpacotamentoServiceImpl implements EmpacotamentoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public EmpacotamentoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public ListaPedidosResponseDTO processarPedidos(ListaPedidosDTO listaPedidos) {
        List<PedidoResponseDTO> pedidosResponse = new ArrayList<>();

        for (PedidoDTO PedidoDTO : listaPedidos.getPedidos()) {
            // Salvar pedido no banco
            Pedido pedido = salvarPedido(PedidoDTO);

            // Processar empacotamento
            PedidoResponseDTO pedidoResponse = processarEmpacotamento(pedido);
            pedidosResponse.add(pedidoResponse);
        }

        return new ListaPedidosResponseDTO(pedidosResponse);
    }

    private Pedido salvarPedido(PedidoDTO pedidoDTO) {
        if (pedidoDTO.getPedidoId() == null) {
            throw new IllegalArgumentException("PedidoId não pode ser nulo");
        }

        Pedido pedido = new Pedido(pedidoDTO.getPedidoId());

        for (ProdutoDTO produtoDto : pedidoDTO.getProdutos()) {
            DimensaoDTO dim = produtoDto.getDimensoes();
            if (dim == null) {
                throw new IllegalArgumentException(
                        "Dimensões não podem ser nulas para o produto: " + produtoDto.getProdutoId());
            }

            Produto produto = new Produto(
                    produtoDto.getProdutoId(),
                    dim.getAltura(),
                    dim.getLargura(),
                    dim.getComprimento());
            pedido.adicionarProduto(produto);
        }

        return pedidoRepository.save(pedido);
    }

    private PedidoResponseDTO processarEmpacotamento(Pedido pedido) {
        List<Produto> produtos = new ArrayList<>(pedido.getProdutos());
        List<CaixaResponseDTO> caixas = new ArrayList<>();

        // Ordenar produtos por volume (maior primeiro)
        produtos.sort((p1, p2) -> Long.compare(p2.getVolume(), p1.getVolume()));

        while (!produtos.isEmpty()) {
            CaixaResponseDTO melhorCaixa = encontrarMelhorCaixa(produtos);
            if (melhorCaixa != null) {
                caixas.add(melhorCaixa);
                // Remover produtos que foram empacotados
                produtos.removeIf(p -> melhorCaixa.getProdutos().contains(p.getProdutoId()));
            } else {
                // Produto não cabe em nenhuma caixa
                Produto produto = produtos.remove(0);
                caixas.add(new CaixaResponseDTO(
                        null,
                        List.of(produto.getProdutoId()),
                        "Produto não cabe em nenhuma caixa disponível."));
            }
        }

        return new PedidoResponseDTO(pedido.getPedidoId(), caixas);
    }

    private CaixaResponseDTO encontrarMelhorCaixa(List<Produto> produtos) {
        TipoCaixa melhorTipoCaixa = null;
        List<String> melhorCombinacao = new ArrayList<>();
        long melhorAproveitamento = 0;

        for (TipoCaixa tipoCaixa : TipoCaixa.values()) {
            List<String> combinacao = encontrarCombinacaoProdutos(produtos, tipoCaixa);
            if (!combinacao.isEmpty()) {
                long volumeUtilizado = calcularVolumeUtilizado(produtos, combinacao);
                long aproveitamento = (volumeUtilizado * 100) / tipoCaixa.getVolume();

                if (aproveitamento > melhorAproveitamento) {
                    melhorAproveitamento = aproveitamento;
                    melhorTipoCaixa = tipoCaixa;
                    melhorCombinacao = combinacao;
                }
            }
        }

        if (melhorTipoCaixa != null) {
            return new CaixaResponseDTO(melhorTipoCaixa.getNome(), melhorCombinacao);
        }

        return null;
    }

    private List<String> encontrarCombinacaoProdutos(List<Produto> produtos, TipoCaixa tipoCaixa) {
        List<String> combinacao = new ArrayList<>();
        long volumeRestante = tipoCaixa.getVolume();

        for (Produto produto : produtos) {
            if (produtoCabeNaCaixa(produto, tipoCaixa) && produto.getVolume() <= volumeRestante) {
                combinacao.add(produto.getProdutoId());
                volumeRestante -= produto.getVolume();
            }
        }

        return combinacao;
    }

    private boolean produtoCabeNaCaixa(Produto produto, TipoCaixa tipoCaixa) {
        // Verificar se o produto cabe em qualquer orientação
        List<Integer> produtoDimensoes = Arrays.asList(
                produto.getAltura(), produto.getLargura(), produto.getComprimento());
        List<Integer> caixaDimensoes = Arrays.asList(
                tipoCaixa.getAltura(), tipoCaixa.getLargura(), tipoCaixa.getComprimento());

        Collections.sort(produtoDimensoes);
        Collections.sort(caixaDimensoes);

        return produtoDimensoes.get(0) <= caixaDimensoes.get(0) &&
                produtoDimensoes.get(1) <= caixaDimensoes.get(1) &&
                produtoDimensoes.get(2) <= caixaDimensoes.get(2);
    }

    private long calcularVolumeUtilizado(List<Produto> produtos, List<String> combinacao) {
        return produtos.stream()
                .filter(p -> combinacao.contains(p.getProdutoId()))
                .mapToLong(Produto::getVolume)
                .sum();
    }
}
