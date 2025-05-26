package br.com.l2code.servico_de_empacotamento.models.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class ListaPedidosDTO {

    @NotEmpty
    @Valid
    private List<PedidoDTO> pedidos;

    public ListaPedidosDTO() {
    }

    public ListaPedidosDTO(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "ListaPedidosDTO [pedidos=" + pedidos + "]";
    }
}