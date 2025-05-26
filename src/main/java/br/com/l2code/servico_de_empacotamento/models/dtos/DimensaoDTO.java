package br.com.l2code.servico_de_empacotamento.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DimensaoDTO {

    @NotNull
    @Positive
    private Integer altura;

    @NotNull
    @Positive
    private Integer largura;

    @NotNull
    @Positive
    private Integer comprimento;

    public DimensaoDTO() {
    }

    public DimensaoDTO(Integer altura, Integer largura, Integer comprimento) {
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getLargura() {
        return largura;
    }

    public void setLargura(Integer largura) {
        this.largura = largura;
    }

    public Integer getComprimento() {
        return comprimento;
    }

    public void setComprimento(Integer comprimento) {
        this.comprimento = comprimento;
    }

    @Override
    public String toString() {
        return "DimensaoDTO [altura=" + altura + ", largura=" + largura + ", comprimento=" + comprimento + "]";
    }
}
