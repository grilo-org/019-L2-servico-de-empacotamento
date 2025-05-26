package br.com.l2code.servico_de_empacotamento.services;

import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosResponseDTO;

public interface EmpacotamentoService {
    ListaPedidosResponseDTO processarPedidos(ListaPedidosDTO listaPedidos);
}
