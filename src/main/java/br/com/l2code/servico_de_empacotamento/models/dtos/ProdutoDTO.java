package br.com.l2code.servico_de_empacotamento.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO {

    @NotBlank
    @JsonProperty("produto_id")
    private String produtoId;

    @NotNull
    @Valid
    private DimensaoDTO dimensoes;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String produtoId, DimensaoDTO dimensoes) {
        this.produtoId = produtoId;
        this.dimensoes = dimensoes;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public DimensaoDTO getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(DimensaoDTO dimensoes) {
        this.dimensoes = dimensoes;
    }

    @Override
    public String toString() {
        return "ProdutoDTO [produtoId=" + produtoId + ", dimensoes=" + dimensoes + "]";
    }
}
