package br.com.l2code.servico_de_empacotamento.models.dtos;

import java.util.List;

public class ListaPedidosResponseDTO {

    private List<PedidoResponseDTO> pedidos;

    public ListaPedidosResponseDTO() {
    }

    public ListaPedidosResponseDTO(List<PedidoResponseDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public List<PedidoResponseDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoResponseDTO> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "ListaPedidosResponseDTO [pedidos=" + pedidos + "]";
    }
}
