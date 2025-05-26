package br.com.l2code.servico_de_empacotamento.enumareted;

public enum TipoCaixa {
    CAIXA_1("Caixa 1", 30, 40, 80),
    CAIXA_2("Caixa 2", 80, 50, 40),
    CAIXA_3("Caixa 3", 50, 80, 60);

    private final String nome;
    private final int altura;
    private final int largura;
    private final int comprimento;

    TipoCaixa(String nome, int altura, int largura, int comprimento) {
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public String getNome() {
        return nome;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public int getComprimento() {
        return comprimento;
    }

    public long getVolume() {
        return (long) altura * largura * comprimento;
    }
}
