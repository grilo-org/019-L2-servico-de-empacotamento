package br.com.l2code.servico_de_empacotamento.models.dtos;

import java.util.List;

public class CaixaResponseDTO {

    private String caixaId;
    private List<String> produtos;
    private String observacao;

    public CaixaResponseDTO() {
    }

    public CaixaResponseDTO(String caixaId, List<String> produtos) {
        this.caixaId = caixaId;
        this.produtos = produtos;
    }

    public CaixaResponseDTO(String caixaId, List<String> produtos, String observacao) {
        this.caixaId = caixaId;
        this.produtos = produtos;
        this.observacao = observacao;
    }

    public String getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(String caixaId) {
        this.caixaId = caixaId;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "CaixaResponseDTO [caixaId=" + caixaId + ", produtos=" + produtos + ", observacao=" + observacao + "]";
    }
}