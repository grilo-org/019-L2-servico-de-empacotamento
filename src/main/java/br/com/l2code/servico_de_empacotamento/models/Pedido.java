package br.com.l2code.servico_de_empacotamento.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pedido_id", nullable = false, unique = true)
    private Integer pedidoId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        produto.setPedido(this);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", produtos=" + produtos +
                '}';
    }
}
