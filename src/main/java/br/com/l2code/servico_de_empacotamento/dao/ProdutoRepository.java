package br.com.l2code.servico_de_empacotamento.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.l2code.servico_de_empacotamento.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByPedidoId(Long pedidoId);
}
