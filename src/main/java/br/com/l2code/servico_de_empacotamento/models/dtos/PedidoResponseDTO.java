package br.com.l2code.servico_de_empacotamento.models.dtos;

import java.util.List;

public class PedidoResponseDTO {

    private Integer pedidoId;
    private List<CaixaResponseDTO> caixas;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Integer pedidoId, List<CaixaResponseDTO> caixas) {
        this.pedidoId = pedidoId;
        this.caixas = caixas;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public List<CaixaResponseDTO> getCaixas() {
        return caixas;
    }

    public void setCaixas(List<CaixaResponseDTO> caixas) {
        this.caixas = caixas;
    }

    @Override
    public String toString() {
        return "PedidoResponseDTO [pedidoId=" + pedidoId + ", caixas=" + caixas + "]";
    }
}
