package br.com.l2code.servico_de_empacotamento.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.l2code.servico_de_empacotamento.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findByPedidoId(Integer pedidoId);

    boolean existsByPedidoId(Integer pedidoId);
}