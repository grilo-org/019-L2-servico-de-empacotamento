package br.com.l2code.servico_de_empacotamento.models.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoDTO {

    @NotNull
    @JsonProperty("pedido_id")
    private Integer pedidoId;

    @NotEmpty
    @Valid
    private List<ProdutoDTO> produtos;

    public PedidoDTO() {
    }

    public PedidoDTO(Integer pedidoId, List<ProdutoDTO> produtos) {
        this.pedidoId = pedidoId;
        this.produtos = produtos;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "PedidoDTO [pedidoId=" + pedidoId + ", produtos=" + produtos + "]";
    }
}
