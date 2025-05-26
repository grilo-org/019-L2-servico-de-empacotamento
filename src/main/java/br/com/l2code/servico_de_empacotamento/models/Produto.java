package br.com.l2code.servico_de_empacotamento.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tbl_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "produto_id", nullable = false)
    private String produtoId;

    @NotNull
    @Positive
    @Column(name = "altura", nullable = false)
    private Integer altura;

    @NotNull
    @Positive
    @Column(name = "largura", nullable = false)
    private Integer largura;

    @NotNull
    @Positive
    @Column(name = "comprimento", nullable = false)
    private Integer comprimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Produto() {
    }

    public Produto(String produtoId, Integer altura, Integer largura, Integer comprimento) {
        this.produtoId = produtoId;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public Long getVolume() {
        return (long) altura * largura * comprimento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", produtoId='" + produtoId + '\'' +
                ", altura=" + altura +
                ", largura=" + largura +
                ", comprimento=" + comprimento +
                ", volume=" + getVolume() +
                '}';
    }
}
